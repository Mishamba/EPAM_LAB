package com.epam.esm.dao.queue;

public class UserQueryRepository {
    public final static String SELECT_ALL_USERS_QUERY = "SELECT e FROM User e";

    public final static String SELECT_USER_BY_ID_QUERY = "SELECT e FROM User e WHERE e.id = ?";
}
