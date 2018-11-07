package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.Section;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(conn -> {
            Resume r;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                r = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, r);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, r);
                }
            }

            return r;
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        ps.executeUpdate();
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    deleteContacts(conn, r);
                    deleteSections(conn, r);
                    insertContact(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContact(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("uuid");
                            Resume resume = new Resume(uuid, rs.getString("full_name"));
                            resumes.put(uuid, resume);
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact c")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("resume_uuid");
                            Resume r = resumes.get(uuid);
                            if (r != null) {
                                addContact(rs, r);
                            }
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section s ")) {
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            String uuid = rs.getString("resume_uuid");
                            Resume r = resumes.get(uuid);
                            if (r != null) {
                                addSection(rs, r);
                            }
                        }
                    }
                    return new ArrayList<>(resumes.values());
                }
        );
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.addContact(type, value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        String description = rs.getString("description");
        if (description != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
            r.addSection(type, JsonParser.read(description, Section.class));
        }
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, description) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, Section> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                Section section = e.getValue();
                ps.setString(3, JsonParser.write(section, Section.class));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }


    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section s WHERE s.resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}
