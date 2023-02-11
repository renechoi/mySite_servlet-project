package com.javaex.manager;

import com.javaex.dao.BoardDao;
import com.javaex.dao.DaoResult;
import com.javaex.vo.BoardVo;

import java.sql.SQLException;

public class BoardManager implements Manager {

    private final BoardDao BOARD_DAO = new BoardDao();

    private static final BoardManager instance = new BoardManager();

    public static BoardManager getInstance() {
        return instance;
    }

    public DaoResult readBy(String condition, BoardVo boardVo) {
        try {
            return BOARD_DAO.readBy(condition, boardVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }


    public DaoResult update(BoardVo boardVo) {
        try {
            return BOARD_DAO.update(boardVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult insert(BoardVo boardVo) {
        try {
            return BOARD_DAO.insert(boardVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult delete(BoardVo boardVo) {
        try {
            return BOARD_DAO.delete(boardVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }
}
