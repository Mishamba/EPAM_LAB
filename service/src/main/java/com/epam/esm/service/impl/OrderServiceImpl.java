package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public List<Order> findUserOrders(int userId, int pageNumber) throws ServiceException {
        if (pageNumber < 1) {
            throw new ServiceException("page number must be positive");
        }

        try {
            return orderDao.findUserOrders(userId, pageNumber);
        } catch (DaoException e) {
            logger.error("can't find orders");
            throw new ServiceException("can't find orders", e);
        }
    }

    @Override
    public boolean createOrder(Order order) throws ServiceException {
        try {
            return orderDao.createOrder(order);
        } catch (DaoException e) {
            logger.error("can't create order");
            throw new ServiceException("can't create order", e);
        }
    }
}
