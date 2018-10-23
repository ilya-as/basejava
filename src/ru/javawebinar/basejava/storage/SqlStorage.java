package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        try (PreparedStatement ps = sqlHelper.prepareQuery("DELETE FROM resume")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (PreparedStatement ps = sqlHelper.prepareQuery("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        try (PreparedStatement ps = sqlHelper.prepareQuery("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            ps.executeUpdate();
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume r) {
        try (PreparedStatement ps = sqlHelper.prepareQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw sqlHelper.parseException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (PreparedStatement ps = sqlHelper.prepareQuery("DELETE FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (PreparedStatement ps = sqlHelper.prepareQuery("SELECT * FROM resume r ORDER BY full_name")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return resumes;
    }

    @Override
    public int size() {
        int size = 0;
        try (PreparedStatement ps = sqlHelper.prepareQuery("SELECT count(*) FROM resume")) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                size = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return size;
    }
}
