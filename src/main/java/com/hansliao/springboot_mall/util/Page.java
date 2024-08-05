package com.hansliao.springboot_mall.util;

import java.util.List;

public class Page<T> {
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public List<T> getResults() {
        return results;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotal() {
        return total;
    }

    
}
