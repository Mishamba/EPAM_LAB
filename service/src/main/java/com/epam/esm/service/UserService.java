package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;

import java.util.List;

public interface UserService {
    List<User> findAllUsers(PaginationData paginationData) throws ServiceException;
    User findUserById(int id);
    Tag userWidelyUsedTag();
}
