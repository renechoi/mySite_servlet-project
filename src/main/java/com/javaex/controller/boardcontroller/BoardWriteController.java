package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BoardWriteController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {

        UserVo authUser = getAuthUser(request);

        BoardVo boardVo = new BoardVo(
                request.getParameter("title"),
                request.getParameter("content"),
                authUser.getNo());

        boardManager.insert(boardVo);
        return new ModelView("/board/list");
    }

    protected UserVo getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        return authUser;
    }
}
