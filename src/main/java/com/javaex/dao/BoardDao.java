package com.javaex.dao;

import com.javaex.jdbc.JdbcTemplate;
import com.javaex.vo.BoardVo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDao implements Dao<BoardVo> {

    public static final JdbcTemplate JDBC_TEMPLATE = JdbcTemplate.getInstance();

    private enum SqlQueries {
        INSERT("insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?)"),
        UPDATE("update board set title = ?, content = ? where no = ?"),
        READ_BY_EACH("select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name from board b, users u where b.user_no = u.no and b.no = ?"),
        READ_BY_ALL("select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name from board b, users u where b.user_no = u.no order by no desc"),
        DELETE("delete from board where no = ?");

        private final String query;

        SqlQueries(String query) {
            this.query = query;
        }
    }

    @Override
    public DaoResult insert(BoardVo boardVo) throws SQLException {
        int result = JDBC_TEMPLATE.executeInsert(SqlQueries.INSERT.query, preparedStatement -> {
            preparedStatement.setString(1, boardVo.getTitle());
            preparedStatement.setString(2, boardVo.getContent());
            preparedStatement.setInt(3, boardVo.getUserNo());
        });
//        JDBC_TEMPLATE.close();
        return new DaoResult(result);
    }

    @Override
    public DaoResult readBy(String condition, BoardVo boardVo) throws SQLException {
        if (condition.equals("All")) {
            return readByAll();
        }
        return readByEach(boardVo);
    }

    private DaoResult readByAll() throws SQLException {
        List<BoardVo> boardVos = new ArrayList<>();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_ALL.query);

        while (resultSet.next()) {
            BoardVo vo = new BoardVo(resultSet.getInt("no"),
                    resultSet.getString("title"),
                    resultSet.getInt("hit"),
                    resultSet.getString("reg_date"),
                    resultSet.getInt("user_no"),
                    resultSet.getString("name"));
            boardVos.add(vo);
        }

        boolean resultExist = resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("BoardVos", boardVos);

//        JDBC_TEMPLATE.close();
        return daoResult;
    }

    private DaoResult readByEach(BoardVo boardVo) throws SQLException {
        int no = boardVo.getNo();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_EACH.query,
                preparedStatement -> preparedStatement.setInt(1, no));

        boolean resultExist = resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("boardVo", new BoardVo(
                no,
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getInt("hit"),
                resultSet.getString("reg_date"),
                resultSet.getInt("user_no"),
                resultSet.getString("name")));

        return daoResult;
    }
    
    @Override
    public DaoResult update(BoardVo boardVo) throws SQLException {
        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.UPDATE.query, preparedStatement -> {
            preparedStatement.setString(1, boardVo.getTitle());
            preparedStatement.setString(2, boardVo.getContent());
            preparedStatement.setInt(3, boardVo.getNo());
        });

//        JDBC_TEMPLATE.close();
        return new DaoResult(result);
    }

    @Override
    public DaoResult delete(BoardVo boardVo) throws SQLException {
        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.DELETE.query, preparedStatement -> {
            preparedStatement.setInt(1, boardVo.getNo());
        });
//        JDBC_TEMPLATE.close();
        return new DaoResult(result);
    }
}
