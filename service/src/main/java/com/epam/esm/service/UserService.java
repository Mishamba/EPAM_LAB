package com.epam.esm.service;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.exception.ServiceException;

import java.awt.print.Pageable;
import java.util.List;

public interface UserService {
    List<User> getAll(int pageNumber, String sortBy, String sotrType) throws ServiceException;
    User getUserById(int id) throws ServiceException;
}
