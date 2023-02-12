package com.javaex.manager;

import com.javaex.dao.DaoResult;
import com.javaex.dao.ReadCondition;
import com.javaex.dao.UserDao;
import com.javaex.vo.UserVo;

import java.io.IOException;
import java.sql.SQLException;

public class UserManager implements Manager{

    private final UserDao USER_DAO = new UserDao();

    private static final UserManager instance = new UserManager();

    public static UserManager getInstance() {
        return instance;
    }

    public DaoResult register(UserVo userVo) {
        try {
            return USER_DAO.insert(userVo);
        } catch (RuntimeException | SQLException | IOException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult idCheck(String email) {
        try {
            return USER_DAO.idCheck(email);
        } catch (RuntimeException | SQLException | IOException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult update(UserVo userVo) {
        try {
            return USER_DAO.update(userVo);
        } catch (RuntimeException | SQLException | IOException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult readByUserNumber(UserVo userVo) {
        try {
            return USER_DAO.readBy(new ReadCondition("userNumber", 0),userVo);
        } catch (RuntimeException | SQLException | IOException e) {
            return new DaoResult("fail");
        }
    }

    public DaoResult readByEmailAndPassword(UserVo userVo) {
        try {
            return USER_DAO.readBy(new ReadCondition("userEmailAndPassword", 0),userVo);
        } catch (RuntimeException | SQLException | IOException e) {
            System.out.println("e = " + e.getMessage());
            return new DaoResult("fail");
        }
    }
}
