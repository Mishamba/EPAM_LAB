package com.epam.esm.model.util.comparator.order.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.util.comparator.AscDescCheck;

import java.util.Comparator;

public class OrderDateComparator extends AscDescCheck implements Comparator<Order> {
    public OrderDateComparator(String sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Order order, Order anotherOrder) {
        int result = order.getOrderDate().compareTo(anotherOrder.getOrderDate());
        return correctOrder(result);
    }
}
