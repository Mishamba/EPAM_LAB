package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getAll(int page) throws DaoException {
        return null;
    }

    @Override
    public User getById(int id) throws DaoException {
        return null;
    }
}
