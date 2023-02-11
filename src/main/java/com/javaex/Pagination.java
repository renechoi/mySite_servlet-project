package com.javaex;

public class Pagination {
    private int articleTotalCount;
    private int articlePerPage;
    private int pageCount;
    private int lagePageArticleCount;
    private int articleStart;
    private int articleEnd;
    private int currentPage;


    public Pagination(int pageLimit, int pageStart, int articleTotalCount) {
        this.articleTotalCount = articleTotalCount;
        this.articlePerPage = pageLimit;
        this.pageCount = createPageCount(pageLimit);
        this.lagePageArticleCount = createLastPage(pageLimit);
        this.articleStart = createArticleStart(pageStart, pageLimit);
        this.articleEnd = createArticleEnd(pageStart, pageLimit);
        this.currentPage = pageStart;
    }

    private int createArticleStart(int pageStart, int pageLimit) {
        // 9개, 리밋 3개인 경우
        // 1 -> 1 ~ 3   (0 * 3) + 1
        // 2 -> 4 ~ 6   (1 * 3) + 1
        // 3 -> 7 ~ 9   (2 * 3) + 1
        return ((pageStart - 1) * pageLimit) + 1;
    }

    private int createArticleEnd(int pageStart, int pageLimit) {
        return articleStart + pageLimit - 1;
    }

    private int createPageCount(int pageLimit) {
        if (createLastPage(pageLimit) == 0) {
            return articleTotalCount / pageLimit;
        }
        return articleTotalCount / pageLimit + 1;
    }

    private int createLastPage(int pageLimit) {
        return articleTotalCount % pageLimit;
    }

    public int getArticleTotalCount() {
        return articleTotalCount;
    }

    public int getArticlePerPage() {
        return articlePerPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getLagePageArticleCount() {
        return lagePageArticleCount;
    }

    public int getArticleStart() {
        return articleStart;
    }

    public int getArticleEnd() {
        return articleEnd;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setArticleTotalCount(int articleTotalCount) {
        this.articleTotalCount = articleTotalCount;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "articleTotalCount=" + articleTotalCount +
                ", articlePerPage=" + articlePerPage +
                ", pageCount=" + pageCount +
                ", lagePageArticleCount=" + lagePageArticleCount +
                ", articleStart=" + articleStart +
                ", articleEnd=" + articleEnd +
                '}';
    }
}
