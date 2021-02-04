package com.epam.esm.util.comparator.order.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.util.comparator.AscDescCheck;

import java.util.Comparator;

public class OrderIdComparator extends AscDescCheck implements Comparator<Order> {
    public OrderIdComparator(String sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(Order order, Order anotherOrder) {
        int result = calculateCompare(order, anotherOrder);
        return correctOrder(result);
    }

    private int calculateCompare(Order order, Order anotherOrder) {
        int result = 0;

        if (order.getId() < anotherOrder.getId()) {
            result = 1;
        }

        if (order.getId() > anotherOrder.getId()) {
            result = -1;
        }

        // We don't need to check id equals case, because of previous checks.

        return result;
    }
}
