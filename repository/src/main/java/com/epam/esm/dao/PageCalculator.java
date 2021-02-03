package com.epam.esm.dao;

public abstract class PageCalculator {
    protected int calculatePageStart(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
    }

    protected int calculatePageEnd(int pageNumber, int pageSize) {
        return pageNumber * pageSize;
    }
}
