package com.javaex.dao;

public class ReadCondition {
    private String searchCondition;
    private int startPage;
    private int pageLimit;

    public ReadCondition(String searchCondition) {
        this(searchCondition, 0, 0);
    }

    public ReadCondition(String searchCondition, int startPage) {
        this(searchCondition,startPage,0);
    }

    public ReadCondition(String searchCondition, int startPage, int pageLimit) {
        this.searchCondition = searchCondition;
        this.startPage = startPage;
        this.pageLimit = pageLimit;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getPageLimit() {
        return pageLimit;
    }
}
