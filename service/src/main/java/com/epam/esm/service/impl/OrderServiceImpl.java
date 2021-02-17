package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.dto.CertificateDTO;
import com.epam.esm.model.entity.dto.OrderDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.comparator.order.OrderComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final CertificateDao certificateDao;
    private final OrderComparatorFactory orderComparatorFactory;
    private final Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, CertificateDao certificateDao,
                            OrderComparatorFactory orderComparatorFactory) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.certificateDao = certificateDao;
        this.orderComparatorFactory = orderComparatorFactory;
    }

    @Override
    public List<OrderDTO> findUserOrders(int userId, PaginationData paginationData)
            throws ServiceException {
        List<Order> orders = orderDao.findUserOrders(userId, paginationData.getPageNumber());
        ifNullThrowServiceException(orders);
        sortOrderList(orders, paginationData);
        return orders.stream().map(OrderDTO::createFromOrder).collect(Collectors.toList());
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
    public void createOrder(int userId, int[] orderedCertificateIds) {
        User user = userDao.findById(userId);
        List<Certificate> orderedCertificates = new ArrayList<>();
        for (int id : orderedCertificateIds) {
            orderedCertificates.add(certificateDao.findCertificateById(id));
        }

        orderDao.createOrder(new Order(user, orderedCertificates));
    }
}
