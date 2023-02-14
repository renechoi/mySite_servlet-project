package com.javaex.controller.boardcontroller;

import com.javaex.vo.Pagination;
import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.dao.ReadCondition;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class BoardListController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {
        int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));

        DaoResult daoResult = boardManager.readBy(
                new ReadCondition("page", page, 10), null);

        List<BoardVo> boardVos = (List<BoardVo>) daoResult.getResultValue().get("BoardVos");
        Pagination pagination = (Pagination) daoResult.getResultValue().get("Pagination");

        boardVos.forEach(v-> System.out.println("v = " + v));
        request.setAttribute("list", boardVos);
        request.setAttribute("pagination", pagination);
        return new ModelView("forward","/board/list");
    }
}
