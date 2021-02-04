package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;

import java.util.List;

public interface OrderService {
    List<Order> findUserOrders(int userId, PaginationData paginationData) throws ServiceException;
    boolean createOrder(Order order) throws ServiceException;
}
