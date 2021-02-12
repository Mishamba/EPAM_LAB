package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional
    public List<Order> findUserOrders(int userId, int pageNumber) {
        return manager.createQuery("SELECT e FROM Order e WHERE e.user_id = :user_id", Order.class).
                setParameter("user_id", userId).setMaxResults(PageSizeConstant.ORDER_PAGE_SIZE).
                setFirstResult(PageSizeConstant.ORDER_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    @Transactional
    public void createOrder(Order order) {
        manager.persist(order);
    }
}
