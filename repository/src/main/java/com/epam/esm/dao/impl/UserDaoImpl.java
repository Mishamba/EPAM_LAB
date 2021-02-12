package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<User> findAllUsers(int pageNumber) {
        return manager.createQuery("SELECT e FROM User e", User.class).
                setMaxResults(PageSizeConstant.USER_PAGE_SIZE).
                setFirstResult(PageSizeConstant.USER_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    public User findById(int id) {
        return manager.createQuery("SELECT e FROM User e WHERE e.id = :id", User.class).
                setParameter("id", id).getSingleResult();
    }

    @Override
    public Tag findWidelyUsedTag() {
        return manager.createQuery("SELECT t FROM Tag t WHERE ")
    }
}
