package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.dto.TagDTO;
import com.epam.esm.model.entity.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.comparator.user.UserComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final UserComparatorFactory userComparatorFactory;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserComparatorFactory userComparatorFactory) {
        this.userDao = userDao;
        this.userComparatorFactory = userComparatorFactory;
    }

    @Override
    public List<UserDTO> findAllUsers(PaginationData paginationData) throws ServiceException {
        List<User> users = userDao.findAllUsers(paginationData.getPageNumber());
        sortUserList(users, paginationData);
        return users.stream().map(UserDTO::createFromUser).collect(Collectors.toList());
    }

    private void sortUserList(List<User> users, PaginationData paginationData) {
        Comparator<User> comparator = userComparatorFactory.getComparator(paginationData);
        users.sort(comparator);
    }

    @Override
    public UserDTO findUserById(int id) {
        return UserDTO.createFromUser(userDao.findById(id));
    }

    @Override
    public TagDTO userWidelyUsedTag() {
        List<User> users = userDao.findAllUsersWithOrders();
        User mostRichUser = findMostReachUser(users);
        return TagDTO.createFromTag(findWidelyUsedTag(mostRichUser.getOrders()));
    }

    private User findMostReachUser(List<User> users) {
        User mostRichUser = users.get(0);
        int ordersCost = 0;
        for (User user : users) {
            int spentMoney = 0;
            for (Order order : user.getOrders()) {
                spentMoney += order.getCost();
            }

            if (ordersCost < spentMoney) {
                ordersCost = spentMoney;
                mostRichUser = user;
            }
        }

        return mostRichUser;
    }

    private Tag findWidelyUsedTag(List<Order> orders) {
        Map<Tag, Integer> tagIntegerMap = countTagUsedTimes(orders);

        Tag widelyUsedTag = null;
        int timesUsed = 0;
        for (Map.Entry<Tag, Integer> tagIntegerEntry : tagIntegerMap.entrySet()) {
            if (tagIntegerEntry.getValue() > timesUsed) {
                widelyUsedTag = tagIntegerEntry.getKey();
            }
        }

        return widelyUsedTag;
    }

    private Map<Tag, Integer> countTagUsedTimes(List<Order> orders) {
        Map<Tag, Integer> tagIntegerMap = new HashMap<>();
        for (Order order : orders) {
            for (Certificate orderedCertificate : order.getOrderedCertificates()) {
                for (Tag tag : orderedCertificate.getTags()) {
                    if (!tagIntegerMap.containsKey(tag)) {
                        tagIntegerMap.put(tag, 1);
                    } else {
                        int tagUsed = tagIntegerMap.get(tag);
                        tagIntegerMap.replace(tag, ++tagUsed);
                    }
                }
            }
        }

        return tagIntegerMap;
    }
}
