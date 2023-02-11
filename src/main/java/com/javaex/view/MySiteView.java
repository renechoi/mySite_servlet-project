package com.javaex.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class MySiteView {

    private String viewType;
    private String viewPath;


    public MySiteView(String viewPath) {
        this.viewPath = viewPath;
    }

    public MySiteView(String viewType, String viewPath) {
        this.viewType = viewType;
        this.viewPath = viewPath;
    }

    public static MySiteView of(String viewName){
        String viewPath = createForwardPath(viewName);
        return new MySiteView(viewPath);
    }

    public static MySiteView of(String viewType, String viewName){
        return new MySiteView(viewType, viewName);
    }

    public static MySiteView of(Map<String, String> viewTypeAndName){
        String viewType = (String) viewTypeAndName.keySet().toArray()[0];
        String viewName = (String) viewTypeAndName.values().toArray()[0];
        return new MySiteView(viewType, viewName);
    }


    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);

        if (this.viewType.equals("forward")){
            forward(request,response,createForwardPath(viewPath));
            return;
        }
        redirect(request,response,viewPath);
    }

    private static void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.forEach(session::setAttribute);
    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(path);
        requestDispatcher.forward(request, response);
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response,String url)
            throws IOException {
        response.sendRedirect(url);
    }

    private static String createForwardPath(String viewName){
        return "/WEB-INF/views/" + viewName + ".jsp";
    }
}
