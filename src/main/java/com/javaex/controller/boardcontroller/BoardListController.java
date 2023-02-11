package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BoardListController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {
        DaoResult daoResult = boardManager.readBy("All", null);

        List<BoardVo> boardVos = (List<BoardVo>) daoResult.getResultValue().get("BoardVos");

        boardVos.forEach(v-> System.out.println("v = " + v));
        request.setAttribute("list", boardVos);
        return new ModelView("/board/list");

    }
}
