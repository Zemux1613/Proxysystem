package de.proxysystem.database.abstraction;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IStatementResolver<T> {
  T resolve(ResultSet resultSet) throws SQLException;
}
