package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.List;

public interface UserDao {
    List<User> findAllUsers(int pageNumber);
    User findById(int id);
    Tag findWidelyUsedTag();
}
