package com.javaex.controller.usercontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller<UserManager> {
    @Override
    public ModelView process(UserManager userManager, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("authUser");
        session.invalidate();
        return new ModelView("/main/index");
    }
}
