package com.javaex.controller.boardcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.dao.ReadCondition;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;
import com.javaex.vo.FileVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

public class BoardReadController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) {

        BoardVo boardVo = new BoardVo(Integer.parseInt(request.getParameter("no")));
        DaoResult daoResult = boardManager.readBy(new ReadCondition("each",0), boardVo);
        BoardVo boardVo1 = (BoardVo) daoResult.getResultValue().get("boardVo");
        FileVo file = boardVo1.getFile();

        request.setAttribute("boardVo", daoResult.getResultValue().get("boardVo"));
        request.setAttribute("file", file);
        return new ModelView("forward","/board/read");
    }
}
