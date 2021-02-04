package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers(int pageNumber) throws DaoException;
    User findById(int id) throws DaoException;
}
