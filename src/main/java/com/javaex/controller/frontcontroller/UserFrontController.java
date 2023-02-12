package com.javaex.controller.frontcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.controller.usercontroller.*;
import com.javaex.manager.UserManager;
import com.javaex.util.WebUtil;
import com.javaex.view.MySiteView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "userFrontControllerServlet", urlPatterns = "/user/*")
public class UserFrontController extends HttpServlet {

    private final Map<String, Controller<UserManager>> controllerMap = new HashMap<>();

    public UserFrontController() {

        controllerMap.put("joinform", new JoinFormController());
        controllerMap.put("modifyform", new UserModifyFormController());
        controllerMap.put("loginform", new LoginFormController());

        controllerMap.put("join", new JoinController());
        controllerMap.put("idcheck", new IdCheckController());
        controllerMap.put("modify", new UserModifyController());
        controllerMap.put("login", new LoginController());
        controllerMap.put("logout", new LogoutController());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        Controller<UserManager> userController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");
        if (userController == null) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        UserManager userManager = UserManager.getInstance();

        ModelView userModelView = userController.process(userManager, request, response);
        renderView(request, response, userModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView userModelView) throws ServletException, IOException {
        MySiteView.of(userModelView.getViewTypeAndName())
                .render(userModelView.getModel(), request, response);
    }


}
