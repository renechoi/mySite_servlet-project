package com.javaex.controller.usercontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IdCheckController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        DaoResult daoResult = userManager.idCheck(email);

        try {
            response.getWriter().write(daoResult.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ModelView("/user/idcheck");
    }

}
