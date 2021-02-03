package com.epam.esm.dao.queue;

public class UserQueryRepository {
    public final static String SELECT_ALL_USERS_QUERY_PAGED = "SELECT id, user_name FROM users LIMIT ?, ?";

    public final static String SELECT_USER_BY_ID_QUERY = "SELECT id, user_name FROM users WHERE id = ?";
}
