package com.javaex.dao;

import com.javaex.Pagination;
import com.javaex.jdbc.JdbcTemplate;
import com.javaex.vo.BoardVo;
import com.javaex.vo.FileVo;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardDao implements Dao<BoardVo> {

    public static final JdbcTemplate JDBC_TEMPLATE = JdbcTemplate.getInstance();

    private enum SqlQueries {
        INSERT("insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?)"),
        INSERT_WITH_FILE("insert into board values (seq_board_no.nextval, ?, ?, 0, sysdate, ?, ?, ?)"),
        UPDATE("update board set title = ?, content = ? where no = ?"),
        READ_BY_EACH("select b.no, b.title, b.content, b.hit, b.reg_date, b.user_no, u.name,  b.file_data, b.file_name from board b, users u where b.user_no = u.no and b.no = ?"),
        READ_BY_PAGE("""
                SELECT article_inline.*
                FROM (SELECT ROWNUM rnum,
                             board.no,
                             board.title,
                             board.content,
                             board.hit,
                             board.reg_date,
                             board.user_no,
                             board.file_data,
                             board.file_name,
                             users.name
                      FROM BOARD board,
                           USERS users
                      WHERE board.user_no = users.no
                      ORDER BY no) article_inline
                WHERE article_inline.rnum >= ?
                  AND article_inline.rnum <= ?
                """),
        READ_BY_PAGE_WITH_SEARCH("""
                SELECT article_inline.*
                FROM (SELECT ROWNUM rnum,
                             board.no,
                             board.title,
                             board.content,
                             board.hit,
                             board.reg_date,
                             board.user_no,
                             board.file_data,
                             board.file_name,
                             users.name
                      FROM BOARD board,
                           USERS users
                      WHERE board.user_no = users.no
                        AND %s = ?
                      ORDER BY no) article_inline
                WHERE article_inline.rnum >= ?
                  AND article_inline.rnum <= ?
                """),
        READ_BY_ALL("select b.no, b.title, b.hit, b.reg_date, b.user_no, u.name from board b, users u where b.user_no = u.no order by no desc"),
        DELETE("delete from board where no = ?"),
        GET_COUNT("select count(*) from board");

        private final String query;

        SqlQueries(String query) {
            this.query = query;
        }
    }

    @Override
    public DaoResult insert(BoardVo boardVo) throws SQLException, IOException {
        int result = JDBC_TEMPLATE.executeInsert(SqlQueries.INSERT_WITH_FILE.query, preparedStatement -> {
            preparedStatement.setString(1, boardVo.getTitle());
            preparedStatement.setString(2, boardVo.getContent());
            preparedStatement.setInt(3, boardVo.getUserNo());
            preparedStatement.setBlob(4, boardVo.getFile().getFileContent());
            preparedStatement.setString(5, boardVo.getFile().getFileName());
        });
        return new DaoResult(result);
    }

    @Override
    public DaoResult readBy(ReadCondition condition, BoardVo boardVo) throws SQLException, IOException {
        if (condition.isSearchAll()) {
            return readByAll();
        }
        if (condition.isSearchByPage()) {
            Pagination pagination = new Pagination(condition.getPageLimit(), condition.getStartPage(), getArticleCount());
            return readByPage(pagination);
        }
        if (condition.isSearchByUserRequest()) {
            Pagination pagination = new Pagination(condition.getPageLimit(), condition.getStartPage(), getArticleCount());
            return readByUserRequest(pagination, condition);
        }
        return readByEach(boardVo);
    }

    private DaoResult readByAll() throws SQLException {
        List<BoardVo> boardVos = new ArrayList<>();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_ALL.query);

        while (resultSet.next()) {
            BoardVo vo = new BoardVo(
                    resultSet.getInt("no"),
                    resultSet.getString("title"),
                    resultSet.getInt("hit"),
                    resultSet.getString("reg_date"),
                    resultSet.getInt("user_no"),
                    resultSet.getString("name"));
            boardVos.add(vo);
        }

        resultSet.next();
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("BoardVos", boardVos);

        return daoResult;
    }

    private DaoResult readByPage(Pagination pagination) throws SQLException, IOException {

        List<BoardVo> boardVos = new ArrayList<>();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_PAGE.query, preparedStatement -> {
            preparedStatement.setInt(1, pagination.getArticleStart());
            preparedStatement.setInt(2, pagination.getArticleEnd());
        });

        while (resultSet.next()) {

            BoardVo vo = new BoardVo(
                    resultSet.getInt("no"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getInt("hit"),
                    resultSet.getString("reg_date"),
                    resultSet.getInt("user_no"),
                    resultSet.getString("name")
            );

            boardVos.add(vo);
        }
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("BoardVos", boardVos);
        daoResult.setResult("Pagination", pagination);

        return daoResult;
    }

    private DaoResult readByUserRequest(Pagination pagination, ReadCondition condition) throws SQLException, IOException {

        List<BoardVo> boardVos = new ArrayList<>();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(String.format(SqlQueries.READ_BY_PAGE_WITH_SEARCH.query, createSearchAreaQuery(condition)), preparedStatement -> {
            preparedStatement.setString(1, condition.getSearchValue());
            preparedStatement.setInt(2, pagination.getArticleStart());
            preparedStatement.setInt(3, pagination.getArticleEnd());
        });

        while (resultSet.next()) {
            BoardVo vo = new BoardVo(
                    resultSet.getInt("no"),
                    resultSet.getString("title"),
                    resultSet.getString("content"),
                    resultSet.getInt("hit"),
                    resultSet.getString("reg_date"),
                    resultSet.getInt("user_no"),
                    resultSet.getString("name")
            );
            boardVos.add(vo);
        }
        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("BoardVos", boardVos);
        daoResult.setResult("Pagination", pagination);

        return daoResult;
    }

    private DaoResult readByEach(BoardVo boardVo) throws SQLException, IOException {
        int no = boardVo.getNo();
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.READ_BY_EACH.query,
                preparedStatement -> preparedStatement.setInt(1, no));

        boolean resultExist = resultSet.next();

        InputStream fileData = resultSet.getBinaryStream("file_data");
        String fileName = resultSet.getString("file_name");

        FileVo fileVo = null;
        if (fileName != null && fileData != null) {
            fileVo = new FileVo(fileName, 0L, fileData);
        }

        DaoResult daoResult = new DaoResult("success");
        daoResult.setResult("boardVo", new BoardVo(
                no,
                resultSet.getString("title"),
                resultSet.getString("content"),
                resultSet.getInt("hit"),
                resultSet.getString("reg_date"),
                resultSet.getInt("user_no"),
                resultSet.getString("name"),
                fileVo)
        );

        return daoResult;
    }

    @Override
    public DaoResult update(BoardVo boardVo) throws SQLException, IOException {
        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.UPDATE.query, preparedStatement -> {
            preparedStatement.setString(1, boardVo.getTitle());
            preparedStatement.setString(2, boardVo.getContent());
            preparedStatement.setInt(3, boardVo.getNo());
        });

        return new DaoResult(result);
    }

    @Override
    public DaoResult delete(BoardVo boardVo) throws SQLException, IOException {
        int result = JDBC_TEMPLATE.executeUpdate(SqlQueries.DELETE.query, preparedStatement -> {
            preparedStatement.setInt(1, boardVo.getNo());
        });
        return new DaoResult(result);
    }


    private int getArticleCount() throws SQLException {
        ResultSet resultSet = JDBC_TEMPLATE.executeQuery(SqlQueries.GET_COUNT.query);
        int count = 0;
        while (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        return count;
    }

    private String createSearchAreaQuery(ReadCondition condition){
        if (condition.getSearchCondition().equals("name")){
            return "users.name";
        }
        return String.format("board.%s", condition.getSearchCondition());
    }

}
