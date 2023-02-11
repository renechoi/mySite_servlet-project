package com.javaex.dao;

import java.util.HashMap;
import java.util.Map;

public class DaoResult {
    private String message;
    private Map<String, Object> resultValue = new HashMap<>();

    public DaoResult(String message) {
        this.message = message;
    }

    public DaoResult(int message){
        this.message = parseResult(message);
    }

    public void setResult(String valueName, Object value){
        resultValue.put(valueName, value);
    }

    private String parseResult(int message) {
        return message == -1 ? "fail" : "success";
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getResultValue() {
        return resultValue;
    }
}
