package com.epam.esm.model.constant;

/**
 * This class works as model layer constants. Contains public final static constants.
 *
 * @version 1.0
 * @author mishamba
 */

public class Constant {
    /**
     * Used to set id for entity which has no generated id.
     */
    public final static int NOT_SET_ID = 0;

    /**
     * This symbol is used when user trying to get certificate by name or tag and didn't gave one of them.
     */
    public final static String STRANGE_SYMBOL = "~";

    /**
     * Constant stores order of sorting.
     * This constant used as default sorting order.
     */
    public final static String ASC_SORT_TYPE = "ASC";

    /**
     * Constant stores order of sorting.
     */
    public final static String DESC_SORT_TYPE = "DESC";

    /**
     * Field name to sort by.
     */
    public final static String SORT_BY_NAME = "NAME";

    /**
     * Field name to sort by.
     */
    public final static String SORT_BY_DATE = "DATE";

    public final static int CERTIFICATE_PAGE_SIZE = 5;

    public final static int USER_PAGE_SIZE = 5;

    public final static int TAG_PAGE_SIZE = 10;
}
