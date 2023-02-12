package com.javaex.controller.frontcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.controller.boardcontroller.*;
import com.javaex.controller.guestbookcontroller.GuestbookMainController;
import com.javaex.manager.BoardManager;
import com.javaex.util.WebUtil;
import com.javaex.view.MySiteView;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 50
)
@WebServlet(name = "boardFrontControllerServlet", urlPatterns = "/board/*")
public class BoardFrontController extends HttpServlet {

    private final Map<String, Controller<BoardManager>> controllerMap = new HashMap<>();

    public BoardFrontController() {

        controllerMap.put("writeform", new BoardWriteFormController());
        controllerMap.put("modifyform", new BoardModifyFormController());

        controllerMap.put("list", new BoardListController());
        controllerMap.put("read", new BoardReadController());
        controllerMap.put("modify", new BoardModifyController());
        controllerMap.put("write", new BoardWriteController());
        controllerMap.put("delete", new BoardDeleteController());

    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getParameter("a");
        System.out.println("requestURI = " + requestURI);
        Controller<BoardManager> boardController = controllerMap.get(requestURI);
        response.setContentType("text/html; charset=UTF-8");
        if (boardController == null) {
            response.sendRedirect("");
            return;
        }

        BoardManager boardManager = BoardManager.getInstance();

        ModelView boardModelView = boardController.process(boardManager, request, response);

        renderView(request, response, boardModelView);
    }

    private static void renderView(HttpServletRequest request, HttpServletResponse response, ModelView userModelView) throws ServletException, IOException {
        MySiteView.of(userModelView.getViewTypeAndName())
                .render(userModelView.getModel(), request, response);
    }


}
