package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlGetter<T> {
    T getRecords(PreparedStatement ps) throws SQLException;
}
