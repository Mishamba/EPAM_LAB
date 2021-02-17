package com.epam.esm.model.util.comparator;

import com.epam.esm.model.constant.SortOrderConstant;

public abstract class AscDescCheck {
    private final String sortOrder;

    public AscDescCheck(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    protected int correctOrder(int compareResult) {
        if (sortOrder.equals(SortOrderConstant.DESC_SORT_TYPE)) {
            if (compareResult > 0) {
                compareResult = -1;
            }
            if (compareResult < 0) {
                compareResult = 1;
            }
        }

        return compareResult;
    }
}
