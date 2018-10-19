package ru.javawebinar.basejava.sql;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeQuery(String textQuery) {
        try {
            prepareQuery(textQuery).executeUpdate();
        } catch (SQLException ex) {
            throw parseException(ex);
        }
    }

    public ResultSet readRecords(String textQuery) {
        ResultSet resultSet = null;
        try {
            resultSet = prepareQuery(textQuery).executeQuery();
        } catch (SQLException ex) {
            throw parseException(ex);
        }
        return resultSet;
    }

    private PreparedStatement prepareQuery(String textQuery) throws SQLException {
        Connection conn = connectionFactory.getConnection();
        PreparedStatement ps = conn.prepareStatement(textQuery);
        return ps;
    }

    private StorageException parseException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);
    }

}

