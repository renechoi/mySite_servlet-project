package com.javaex.controller.frontcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.controller.guestbookcontroller.GuestbookAddController;
import com.javaex.controller.guestbookcontroller.GuestbookDeleteController;
import com.javaex.controller.guestbookcontroller.GuestbookDeleteFormController;
import com.javaex.controller.guestbookcontroller.GuestbookMainController;
import com.javaex.manager.GuestbookManager;
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


@WebServlet(name = "guestbookFrontControllerServlet", urlPatterns = "/guestbook/*")
public class GuestbookFrontController extends HttpServlet {

    private final Map<String, Controller<GuestbookManager>> controllerMap = new HashMap<>();

    public GuestbookFrontController() {

        controllerMap.put("add", new GuestbookAddController());
        controllerMap.put("deleteform", new GuestbookDeleteFormController());
        controllerMap.put("delete", new GuestbookDeleteController());
        controllerMap.put("list", new GuestbookMainController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        System.out.println("requestURI = " + requestURI);
        Controller<GuestbookManager> guestbookController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");

        if (guestbookController == null) {
//            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            guestbookController = new GuestbookMainController();
//            return;
        }

        GuestbookManager guestbookManager = GuestbookManager.getInstance();

        ModelView guestbookModelView = guestbookController.process(guestbookManager, request, response);

        renderView(request, response, guestbookModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView guestbookModelView) throws ServletException, IOException {
        MySiteView.of(guestbookModelView.getViewTypeAndName())
                .render(guestbookModelView.getModel(), request, response);
    }
}
