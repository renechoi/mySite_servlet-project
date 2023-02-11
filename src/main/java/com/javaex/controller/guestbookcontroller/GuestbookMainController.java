package com.javaex.controller.guestbookcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.dao.DaoResult;
import com.javaex.manager.GuestbookManager;
import com.javaex.vo.GuestbookVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GuestbookMainController implements Controller<GuestbookManager> {
    @Override
    public ModelView process(GuestbookManager guestbookManager, HttpServletRequest request, HttpServletResponse response) {

        DaoResult daoResult = guestbookManager.readByAll("All", null);
        List<GuestbookVo> guestbookVos = (List<GuestbookVo>) daoResult.getResultValue().get("GuestbookVos");

//        guestbookVos.stream().forEach(guestbookVo -> System.out.println("guestbookVo = " + guestbookVo));

        request.setAttribute("list", guestbookVos);

        return new ModelView("forward","/guestbook/list");
    }
}
