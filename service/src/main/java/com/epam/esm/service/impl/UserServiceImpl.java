package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.comparator.user.UserComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserComparatorFactory userComparatorFactory;
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserDao userDao, UserComparatorFactory userComparatorFactory) {
        this.userDao = userDao;
        this.userComparatorFactory = userComparatorFactory;
    }

    @Override
    public List<User> findAllUsers(PaginationData paginationData) throws ServiceException {
        List<User> users = userDao.findAllUsers(paginationData.getPageNumber());
        sortUserList(users, paginationData);
        return users;
    }

    private void sortUserList(List<User> users, PaginationData paginationData) {
        Comparator<User> comparator = userComparatorFactory.getComparator(paginationData);
        users.sort(comparator);
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public Tag userWidelyUsedTag() {
        return userDao.findWidelyUsedTag();
    }
}
