package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public PreparedStatement runQuery(String runQuery) throws SQLException {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(runQuery);
        return ps;


        /*try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(runQuery)) {
             return ps.execute();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }*/
    }
}
