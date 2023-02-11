package com.javaex.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller<T> {
    ModelView process(T manager, HttpServletRequest request, HttpServletResponse response);

}
