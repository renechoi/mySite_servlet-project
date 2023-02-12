package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;
import com.javaex.vo.FileVo;
import com.javaex.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BoardWriteController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {
        BoardVo boardVo = new BoardVo(
                request.getParameter("title"),
                request.getParameter("content"),
                getAuthUser(request).getNo(),
                getFile(request));

        boardManager.insert(boardVo);
        return new ModelView("forward", "/board/list");
    }

    protected UserVo getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        return authUser;
    }

    private static FileVo getFile(HttpServletRequest request) {
        try {
            Part file = request.getPart("fileName");
            return new FileVo(file.getSubmittedFileName(), file.getSize(), file.getInputStream());
        } catch (IOException | ServletException e) {
            System.out.println("e.getMessage() = " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private String getFilename(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] split = contentDisp.split(";");
        for (int i = 0; i < split.length; i++) {
            String temp = split[i];
            if (temp.trim().startsWith("filename")) {
                return temp.substring(temp.indexOf("=") + 2, temp.length() - 1);
            }
        }
        return "";
    }
}
