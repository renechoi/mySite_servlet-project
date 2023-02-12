package com.javaex.dao;

public class ReadCondition {
    private String searchCondition;
    private String searchValue;
    private int startPage;
    private int pageLimit;

    public ReadCondition(String searchCondition) {
        this(searchCondition, 0, 0);
    }

    public ReadCondition(String searchCondition, String searchValue) {
        this(searchCondition, searchValue, 0, 0);
    }

    public ReadCondition(String searchCondition, int startPage) {
        this(searchCondition,startPage,0);
    }

    public ReadCondition(String searchCondition, int startPage, int pageLimit) {
        this.searchCondition = searchCondition;
        this.startPage = startPage;
        this.pageLimit = pageLimit;
    }

    public ReadCondition(String searchCondition, String searchValue, int startPage, int pageLimit) {
        this.searchCondition = searchCondition;
        this.searchValue = searchValue;
        this.startPage = startPage;
        this.pageLimit = pageLimit;
    }

    public static ReadCondition valueOf(String searchType, String searchValue){
        return new ReadCondition(searchType, searchValue);
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


    public boolean isSearchAll(){
        return searchCondition.equals("all");
    }

    public boolean isSearchByPage(){
        return getSearchCondition().equals("page");
    }

    public String getSearchValue() {
        return searchValue;
    }

    public boolean isSearchByUserRequest() {
        return this.searchCondition.equals("name")
                || this.searchCondition.equals("reg_date")
                || this.searchCondition.equals("title")
                || this.searchCondition.equals("content")
                || this.searchCondition.equals("file_name");
    }

    public ReadCondition withPageLimit(int limit) {
        System.out.println("searchCondition = " + searchCondition);
        System.out.println("searchValue = " + searchValue);
        return new ReadCondition(this.searchCondition, this.searchValue,1,limit);
    }
}
