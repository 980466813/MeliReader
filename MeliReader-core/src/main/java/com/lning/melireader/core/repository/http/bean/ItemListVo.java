package com.lning.melireader.core.repository.http.bean;

import java.util.List;

public class ItemListVo {
    private long totalPage;
    private int page;
    private boolean isFirst;
    private boolean isEnd;
    private List<?> rows;
    private int limit;
    private long totalRows;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ItemListVo{" +
                "totalPage=" + totalPage +
                ", page=" + page +
                ", isFirst=" + isFirst +
                ", isEnd=" + isEnd +
                ", limit=" + limit +
                ", totalRows=" + totalRows +
                '}';
    }
}
