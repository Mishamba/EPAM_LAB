package com.epam.esm.model.util.comparator.user.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.util.comparator.AscDescCheck;

import java.util.Comparator;

public class UserNameComparator extends AscDescCheck implements Comparator<User> {
    public UserNameComparator(String sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(User user, User anotherUser) {
        int result = calculateCompare(user, anotherUser);
        return correctOrder(result);
    }

    private int calculateCompare(User user, User anotherUser) {
        return user.getEmail().compareTo(anotherUser.getEmail());
    }
}
