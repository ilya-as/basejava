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

    public <T> T transactionExecute(String textQuery, SqlRunner<T> sqlRunner) {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(textQuery);
            return sqlRunner.execute(ps);
        } catch (SQLException e) {
            throw parseException(e);
        }
    }

    private StorageException parseException(SQLException e) {
        if (e.getSQLState().equals("23505")) {
            return new ExistStorageException(null);
        }
        return new StorageException(e);
    }

}

