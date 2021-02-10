package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
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
        List<Order> orders = orderDao.findUserOrders(userId, paginationData.getPageNumber());
        ifNullThrowServiceException(orders);
        sortOrderList(orders, paginationData);
        return orders;
    }

    private void sortOrderList(List<Order> orders, PaginationData paginationData) {
        Comparator<Order> comparator = orderComparatorFactory.getComparator(paginationData);
        orders.sort(comparator);
    }

    private void ifNullThrowServiceException(List<Order> certificates) throws ServiceException {
        if (certificates == null) {
            throw new ServiceException("no certificates found", new NullPointerException("list is null"));
        }
    }

    @Override
    public void createOrder(Order order) {
        orderDao.createOrder(order);
    }
}
