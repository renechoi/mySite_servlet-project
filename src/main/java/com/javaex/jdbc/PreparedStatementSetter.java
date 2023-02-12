package com.javaex.jdbc;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void setPreparedStatement(PreparedStatement preparedStatement) throws SQLException, IOException;
}
