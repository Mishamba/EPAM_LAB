package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
    List<Order> findUserOrders(int userId, int pageNumber) throws ServiceException;
    boolean createOrder(Order order) throws ServiceException;
}
