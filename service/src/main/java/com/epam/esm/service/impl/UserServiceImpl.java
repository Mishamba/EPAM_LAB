package com.epam.esm.service.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll(int pageNumber, String sortBy, String sortType) throws ServiceException {
        return null;
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        return null;
    }
}
