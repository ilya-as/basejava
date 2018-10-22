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

    public void executeQuery(String textQuery)  {
        try {
         PreparedStatement ps = prepareQuery(textQuery);
            SqlRunner sqlRunner = (SqlRunner) ps;
            executeQuery(textQuery, sqlRunner);
        } catch (SQLException ex) {
            throw parseException(ex);
        }

    }

    public void executeQuery(String textQuery,SqlRunner sqlRunner) {
        try {
            sqlRunner.runQuery(prepareQuery(textQuery));
        } catch (SQLException ex) {
            throw parseException(ex);
        }
    }

    public <T> T readRecords(String textQuery,SqlGetter sqlGetter) {

        try {
            return (T) sqlGetter.getRecords(prepareQuery(textQuery));
        } catch (SQLException ex) {
            throw parseException(ex);
        }
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

