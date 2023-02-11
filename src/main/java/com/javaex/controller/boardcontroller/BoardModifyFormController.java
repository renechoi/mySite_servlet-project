package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardModifyFormController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {

        BoardVo boardVo = new BoardVo(Integer.parseInt(request.getParameter("no")));
        DaoResult daoResult = boardManager.readBy("each", boardVo);

        request.setAttribute("boardVo", daoResult.getResultValue().get("boardVo"));
        return new ModelView("/board/modifyform");
    }
}
