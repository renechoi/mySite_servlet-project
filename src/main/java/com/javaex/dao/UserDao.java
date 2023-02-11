package com.javaex.dao;

import com.javaex.jdbc.JdbcTemplate;
import com.javaex.vo.UserVo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao implements Dao<UserVo> {

    public static final JdbcTemplate JDBC_TEMPLATE = JdbcTemplate.getInstance();

    private enum SqlQueries {
        INSERT("insert into users values (seq_users_no.nextval, ?, ?, ?, ?)"),
        READ_BY_USER_NO("select no, name, email, password, gender from users where no = ?"),
        READ_BY_USER_EMAIL_AND_PASSWORD("select no, name from users where email = ? and password = ?"),
        UPDATE_WITHOUT_PASSWORD("update users set name = ?, gender = ? where no = ?"),
        UPDATE_WITH_PASSWORD("update users set name = ?, password = ?, gender = ? where no = ?"),
        GET_COUNT_BY_EMAIL("select count(*) count from users  where email = ?"),
        DELETE("");

        private final String query;

        SqlQueries(String query) {
            this.query = query;
        }
    }

    @Override
    public DaoResult insert(UserVo userVo) throws SQLException {
        int result = JDBC_TEMPLATE.executeInsert(SqlQueries.INSERT.query, preparedStatement -> {
            preparedStatement.setString(1, userVo.getName());
            preparedStatement.setString(2, userVo.getEmail());
            preparedStatement.setString(3, userVo.getPassword());
            preparedStatement.setString(4, userVo.getGender());
        });
//        JDBC_TEMPLATE.close();
        return new DaoResult(result);
    }

    @Override
    public DaoResult readBy(String condition, UserVo userVo) throws SQLException {
        if (condition.equals("userNumber")) {
            return readByUserNumber(userVo);
        }
        return readByEmailAndPassword(userVo);
    }


    private DaoResult readByUserNumber(UserVo userVo) throws SQLException {
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_USER_NO.query, preparedStatement -> {
            preparedStatement.setInt(1, userVo.getUserNumber());
        });

        boolean resultExist = resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("userVo", new UserVo(resultSet.getInt("no"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("gender")));

        return daoResult;
    }

    private DaoResult readByEmailAndPassword(UserVo userVo) throws SQLException {
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_USER_EMAIL_AND_PASSWORD.query, preparedStatement -> {
            preparedStatement.setString(1, userVo.getEmail());
            preparedStatement.setString(2, userVo.getPassword());
        });

        boolean resultExist = resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("userVo", new UserVo(
                resultSet.getInt("no"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("gender"))
        );

        return daoResult;
    }

    @Override
    public DaoResult update(UserVo userVo) throws SQLException {
        if (!isPasswordFound(userVo)) {
            int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.UPDATE_WITHOUT_PASSWORD.query, preparedStatement -> {
                preparedStatement.setString(1, userVo.getName());
                preparedStatement.setString(2, userVo.getGender());
                preparedStatement.setInt(3, userVo.getUserNumber());
            });
            return new DaoResult(result);
        }

        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.UPDATE_WITH_PASSWORD.query, preparedStatement -> {
            preparedStatement.setString(1, userVo.getName());
            preparedStatement.setString(2, userVo.getPassword());
            preparedStatement.setString(3, userVo.getGender());
            preparedStatement.setInt(4, userVo.getUserNumber());
        });
//        JDBC_TEMPLATE.close();
        return new DaoResult(result);
    }


    @Override
    public DaoResult delete(UserVo userVo) {
        return null;
    }


    public DaoResult idCheck(String email) throws SQLException {
        String result = getCountByEmail(email) == 1 ? "true" : "false";
    return new DaoResult(result);
    }

    private int getCountByEmail(String email) throws SQLException {
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.GET_COUNT_BY_EMAIL.query, preparedStatement -> {
            preparedStatement.setString(1, email);
        });
        resultSet.next();
        return resultSet.getInt("cnt");
    }

    private static boolean isPasswordFound(UserVo userVo) {
        return userVo.getPassword().isEmpty() || userVo.getPassword().equals(null);
    }

}
