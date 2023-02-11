package com.javaex.manager;

import com.javaex.dao.DaoResult;
import com.javaex.dao.GuestbookDao;
import com.javaex.vo.GuestbookVo;

import java.sql.SQLException;

public class GuestbookManager implements Manager {

    private final GuestbookDao GUESTBOOK_DAO = new GuestbookDao();

    private static final GuestbookManager instance = new GuestbookManager();

    public static GuestbookManager getInstance() {
        return instance;
    }

    public DaoResult insert(GuestbookVo guestbookVo) {
        try {
            return GUESTBOOK_DAO.insert(guestbookVo);
        } catch (RuntimeException | SQLException e) {
            System.out.println("e = " + e.getMessage());
            return new DaoResult("fail");
        }
    }

    public DaoResult delete(GuestbookVo guestbookVo) {
        try {
            return GUESTBOOK_DAO.delete(guestbookVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult readByAll(String condition, GuestbookVo guestbookVo) {
        try {
            return GUESTBOOK_DAO.readBy(condition, guestbookVo);
        } catch (RuntimeException | SQLException e) {
            return new DaoResult("fail");
        }
    }
}
