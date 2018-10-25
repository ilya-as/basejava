package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;
import ru.javawebinar.basejava.sql.SqlRunner;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.transactionExecute("DELETE FROM resume", (SqlRunner<Resume>) ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionExecute("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionExecute("UPDATE resume SET full_name = ? WHERE uuid = ?", (SqlRunner<Resume>) ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.executeUpdate();
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionExecute("INSERT INTO resume (uuid, full_name) VALUES (?,?)", (SqlRunner<Resume>) ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;

        });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.transactionExecute("DELETE FROM resume r WHERE r.uuid =?", (SqlRunner<Resume>) ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        return sqlHelper.transactionExecute("SELECT * FROM resume r ORDER BY full_name", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.transactionExecute("SELECT count(*) FROM resume", ps -> {
            int size = 0;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
            return size;
        });
    }
}
