package com.epam.esm.model.util.comparator.order;

import com.epam.esm.model.constant.OrderSortParametersConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.util.comparator.order.impl.OrderDateComparator;
import com.epam.esm.model.util.comparator.order.impl.OrderIdComparator;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Component
public class OrderComparatorFactory {
    private final Map<String, Map<String, Comparator<Order>>> orderComparator;
    private final Map<String, Comparator<Order>> idOrderComparator;
    private final Map<String, Comparator<Order>> dateOrderComparator;

    public OrderComparatorFactory() {
        this.orderComparator = new HashMap<>();
        this.idOrderComparator = new HashMap<>();
        this.dateOrderComparator = new HashMap<>();
        orderComparator.put(OrderSortParametersConstant.SORT_BY_ID, idOrderComparator);
        orderComparator.put(OrderSortParametersConstant.SORT_BY_ORDER_DATE, dateOrderComparator);
        idOrderComparator.put(SortOrderConstant.ASC_SORT_TYPE, new OrderIdComparator(SortOrderConstant.ASC_SORT_TYPE));
        idOrderComparator.put(SortOrderConstant.DESC_SORT_TYPE,
                new OrderIdComparator(SortOrderConstant.DESC_SORT_TYPE));
        dateOrderComparator.put(SortOrderConstant.ASC_SORT_TYPE,
                new OrderDateComparator(SortOrderConstant.ASC_SORT_TYPE));
        dateOrderComparator.put(SortOrderConstant.DESC_SORT_TYPE,
                new OrderDateComparator(SortOrderConstant.DESC_SORT_TYPE));
    }

    public Comparator<Order> getComparator(PaginationData paginationData) {
        return orderComparator.get(paginationData.getSortBy()).get(paginationData.getSortType());
    }
}
