package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.BoardManager;
import com.javaex.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BoardWriteFormController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager manager, HttpServletRequest request, HttpServletResponse response) {
        UserVo authUser = getAuthUser(request);
        if (authUser != null) {
            return new ModelView("/board/writeform");
        } else { // 로그인 안했으면 리스트로
            return new ModelView("/board/list");
        }
    }

    protected UserVo getAuthUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserVo authUser = (UserVo) session.getAttribute("authUser");

        return authUser;
    }
}
