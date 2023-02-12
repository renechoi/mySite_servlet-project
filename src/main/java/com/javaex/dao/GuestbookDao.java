package com.javaex.dao;

import com.javaex.jdbc.JdbcTemplate;
import com.javaex.vo.GuestbookVo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GuestbookDao implements Dao<GuestbookVo> {


    public static final JdbcTemplate JDBC_TEMPLATE = JdbcTemplate.getInstance();

    private enum SqlQueries {
        INSERT("insert into guestbook values (seq_guestbook_no.nextval, ?, ?, ?,sysdate)"),
        READ_BY_ALL("select no, name, password, content, reg_date from guestbook order by reg_date desc "),
        DELETE("delete from guestbook where no= ? and password= ?");

        private final String query;

        SqlQueries(String query) {
            this.query = query;
        }
    }

    @Override
    public DaoResult insert(GuestbookVo guestbookVo) throws SQLException, IOException {
        int result = JDBC_TEMPLATE.executeInsert(SqlQueries.INSERT.query, preparedStatement -> {
            preparedStatement.setString(1, guestbookVo.getName());
            preparedStatement.setString(2, guestbookVo.getPassword());
            preparedStatement.setString(3, guestbookVo.getContent());
        });
        return new DaoResult(result);
    }

    @Override
    public DaoResult readBy(ReadCondition condition, GuestbookVo guestbookVo) throws SQLException {
        List<GuestbookVo> guestbookVos = new ArrayList<>();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_ALL.query);

        while (resultSet.next()) {
            guestbookVos.add(new GuestbookVo(
                    resultSet.getInt("no"),
                    resultSet.getString("name"),
                    resultSet.getString("password"),
                    resultSet.getString("content"),
                    resultSet.getString("reg_date")));
        }

        boolean resultExist = resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("GuestbookVos", guestbookVos);

//        JDBC_TEMPLATE.close();
        return daoResult;
    }

    @Override
    public DaoResult update(GuestbookVo guestbookVo) throws SQLException {
        return null;
    }

    @Override
    public DaoResult delete(GuestbookVo guestbookVo) throws SQLException, IOException {
        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.DELETE.query, preparedStatement -> {
            preparedStatement.setInt(1, guestbookVo.getNo());
            preparedStatement.setString(2, guestbookVo.getPassword());
        });
//        JDBC_TEMPLATE.close();
        System.out.println("result = " + result);
        return new DaoResult(result);
    }
}
