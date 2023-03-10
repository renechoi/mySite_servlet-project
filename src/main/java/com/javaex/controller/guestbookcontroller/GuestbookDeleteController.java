package com.javaex.controller.guestbookcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.GuestbookManager;
import com.javaex.vo.GuestbookVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestbookDeleteController implements Controller<GuestbookManager> {
    @Override
    public ModelView process(GuestbookManager guestbookManager, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("GuestbookDeleteController.process");
        GuestbookVo guestbookVo = new GuestbookVo(
                Integer.parseInt(request.getParameter("no")),
                request.getParameter("password")
        );


        DaoResult daoResult = guestbookManager.delete(guestbookVo);
        System.out.println("daoResult.getMessage() = " + daoResult.getMessage());

        return new ModelView("redirect","/guestbook?a=list");
    }
}
