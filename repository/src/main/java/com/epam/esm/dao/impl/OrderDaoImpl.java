package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(int userId) throws DaoException {
        return null;
    }

    @Override
    public boolean createOrder(Order order) throws DaoException {
        return false;
    }
}
