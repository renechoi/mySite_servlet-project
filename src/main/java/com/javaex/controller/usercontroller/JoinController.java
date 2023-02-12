package com.javaex.controller.usercontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.UserManager;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JoinController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {

        UserVo userVo = new UserVo(
                request.getParameter("name"),
                request.getParameter("email"),
                request.getParameter("password"),
                request.getParameter("gender"));

        DaoResult daoResult = userManager.register(userVo);

        return new ModelView("forward", "/user/joinsuccess");
    }
}
