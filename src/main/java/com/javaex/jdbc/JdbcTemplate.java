package com.javaex.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplate {

    private static final JdbcTemplate instance = new JdbcTemplate();

    private JdbcTemplate() {
    }

    public static JdbcTemplate getInstance() {
        return instance;
    }

    private Connection connection = ConnectionManager.getConnection();

    public int executeInsert(String sqlQuery, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatementSetter.setPreparedStatement(preparedStatement);

        return preparedStatement.executeUpdate();
    }

    public int executeUpdate(String sqlQuery, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatementSetter.setPreparedStatement(preparedStatement);
        return preparedStatement.executeUpdate();
    }

    public int executeDelete(String sqlQuery, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatementSetter.setPreparedStatement(preparedStatement);

        return preparedStatement.executeUpdate();
    }

    public ResultSet executeQuery(String sqlQuery) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        return preparedStatement.executeQuery();
    }

    public ResultSet executeQuery(String sqlQuery, PreparedStatementSetter preparedStatementSetter) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        preparedStatementSetter.setPreparedStatement(preparedStatement);
        return preparedStatement.executeQuery();
    }


//    public void close() throws SQLException {
//        if (connection!=null){
//            connection.close();
//        }
//    }


}

