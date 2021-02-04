package com.epam.esm.util.comparator.user.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.util.comparator.AscDescCheck;

import java.util.Comparator;

public class UserIdComparator extends AscDescCheck implements Comparator<User> {
    public UserIdComparator(String sortOrder) {
        super(sortOrder);
    }

    @Override
    public int compare(User user, User anotherUser) {
        int result = calculateCompare(user, anotherUser);
        return correctOrder(result);
    }

    private int calculateCompare(User user, User anotherUser) {
        int result = 0;

        if (user.getId() < anotherUser.getId()) {
            result = 1;
        }

        if (user.getId() > anotherUser.getId()) {
            result = -1;
        }

        // We don't need to check id equals case, because of previous checks.

        return result;
    }
}
