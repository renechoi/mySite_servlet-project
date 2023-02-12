package com.javaex.dao;

import java.io.IOException;
import java.sql.SQLException;

public interface Dao<T> {

    DaoResult insert(T t) throws SQLException, IOException;
    DaoResult readBy(ReadCondition condition, T t) throws SQLException, IOException;
    DaoResult update(T t) throws SQLException, IOException;
    DaoResult delete(T t) throws SQLException, IOException;





}
