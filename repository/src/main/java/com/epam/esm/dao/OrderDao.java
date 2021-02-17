package com.epam.esm.dao;

import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> findUserOrders(int userId, int pageNumber);
    void createOrder(Order order);
}
