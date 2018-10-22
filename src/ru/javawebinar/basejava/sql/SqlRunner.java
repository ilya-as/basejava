package ru.javawebinar.basejava.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlRunner{
      void runQuery (PreparedStatement ps) throws SQLException;
}
