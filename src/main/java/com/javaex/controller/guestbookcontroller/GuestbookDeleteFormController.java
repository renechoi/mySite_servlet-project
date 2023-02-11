package com.javaex.controller.guestbookcontroller;

import com.javaex.controller.Controller;
import com.javaex.controller.ModelView;
import com.javaex.manager.GuestbookManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestbookDeleteFormController implements Controller<GuestbookManager> {
    @Override
    public ModelView process(GuestbookManager guestbookManager, HttpServletRequest request, HttpServletResponse response) {
        return new ModelView("/guestbook/deleteform");
    }
}
