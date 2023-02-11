package com.javaex.dao;

import java.sql.SQLException;

public interface Dao<T> {

    DaoResult insert(T t) throws SQLException;
    DaoResult readBy(String condition, T t) throws SQLException;
    DaoResult update(T t) throws SQLException;
    DaoResult delete(T t) throws SQLException;





}
