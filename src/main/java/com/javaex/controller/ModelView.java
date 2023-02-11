package com.javaex.controller;

import java.util.HashMap;
import java.util.Map;

public class ModelView {


    private String viewName;
    private Map<String, String> viewTypeAndName = new HashMap<>();
    private Map<String, Object> model = new HashMap<>();


    public ModelView(String viewName, Map<String, Object> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public ModelView(String viewType, String viewName) {
        viewTypeAndName.put(viewType, viewName);
    }

    public Map<String, String> getViewTypeAndName() {
        return viewTypeAndName;
    }

    public String getViewName() {
        return viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}


