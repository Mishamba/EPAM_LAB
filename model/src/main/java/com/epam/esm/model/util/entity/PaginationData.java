package com.epam.esm.model.util.entity;

import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.SortOrderConstant;

public class PaginationData {
    private String sortBy;
    private String sortType;
    private int pageNumber;

    public PaginationData(String sortBy, String sortType, int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }


        if (sortBy == null || !(sortBy.equals(CertificateSortParametersConstant.SORT_BY_NAME) ||
                sortBy.equals(CertificateSortParametersConstant.SORT_BY_DATE))) {
            sortBy = CertificateSortParametersConstant.SORT_BY_NAME;
        }

        if (sortType == null || !(sortType.equals(SortOrderConstant.DESC_SORT_TYPE) ||
                sortType.equals(SortOrderConstant.ASC_SORT_TYPE))) {
            sortType = SortOrderConstant.ASC_SORT_TYPE;
        }

        this.sortBy = sortBy;
        this.sortType = sortType;
        this.pageNumber = pageNumber;
    }

    public String getSortBy() {
        if (!(sortBy.equals(CertificateSortParametersConstant.SORT_BY_NAME) ||
                sortBy.equals(CertificateSortParametersConstant.SORT_BY_DATE))) {
            sortBy = CertificateSortParametersConstant.SORT_BY_NAME;
        }

        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        if (!(sortType.equals(SortOrderConstant.DESC_SORT_TYPE) || sortType.equals(SortOrderConstant.ASC_SORT_TYPE))) {
            sortType = SortOrderConstant.ASC_SORT_TYPE;
        }

        this.sortType = sortType;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
    }
}
