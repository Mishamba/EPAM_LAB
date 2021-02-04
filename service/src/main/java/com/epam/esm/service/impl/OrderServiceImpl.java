package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.comparator.order.OrderComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderComparatorFactory orderComparatorFactory;
    private final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderComparatorFactory orderComparatorFactory) {
        this.orderDao = orderDao;
        this.orderComparatorFactory = orderComparatorFactory;
    }

    @Override
    public List<Order> findUserOrders(int userId, PaginationData paginationData)
            throws ServiceException {
        try {
            return orderDao.findUserOrders(userId, paginationData.getPageNumber());
        } catch (DaoException e) {
            logger.error("can't find orders");
            throw new ServiceException("can't find orders", e);
        }
    }

    private void sortOrders(List<Order> orders, PaginationData paginationData) {
        Comparator<Order> comparator = orderComparatorFactory.getComparator(paginationData);
        orders.sort(comparator);
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
