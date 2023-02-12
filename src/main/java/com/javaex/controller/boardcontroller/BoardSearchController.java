package com.javaex.controller.boardcontroller;

import com.javaex.Pagination;
import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.dao.ReadCondition;
import com.javaex.manager.BoardManager;
import com.javaex.vo.BoardVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class BoardSearchController implements Controller<BoardManager> {
    @Override
    public ModelView process(BoardManager boardManager, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String searchType = request.getParameter("searchType");  // 작성자, 작성일시, 제목, 내용, 파일이름
        String searchValue = request.getParameter("searchValue");  //username, reg_date, title, content, new FileVo(파일이름)
        System.out.println("searchType = " + searchType);
        System.out.println("searchValue = " + searchValue);

        DaoResult daoResult = boardManager.readBy(
                ReadCondition.valueOf(searchType, searchValue).withPageLimit(10),
                null);

        List<BoardVo> boardVos = (List<BoardVo>) daoResult.getResultValue().get("BoardVos");
        Pagination pagination = (Pagination) daoResult.getResultValue().get("Pagination");

        boardVos.forEach(v-> System.out.println("v = " + v));
        request.setAttribute("list", boardVos);
        request.setAttribute("pagination", pagination);
        return new ModelView("forward","/board/list");
    }
}
