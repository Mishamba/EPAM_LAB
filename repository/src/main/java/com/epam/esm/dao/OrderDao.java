package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getUserOrders(int userId) throws DaoException;
    boolean createOrder(Order order) throws DaoException;
}
