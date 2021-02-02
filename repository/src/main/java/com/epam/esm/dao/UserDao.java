package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAll(int page) throws DaoException;
    User getById(int id) throws DaoException;
}
