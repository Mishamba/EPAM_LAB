package com.epam.esm.dao.queue;

public class OrderQueryRepository {
    public final static String SELECT_ORDERS_BY_USERS_ID = "SELECT id, users_id, order_date FROM orders LIMIT ?, ?";

    public final static String SELECT_ORDERS_CERTIFICATES_IDS =
            "SELECT certificate_id FROM certificate_orders WHERE order_id = ?";

    public final static String CREATE_ORDER_SIGN = "INSERT INTO orders(users_id, order_date) VALUES (?, ?)";

    public final static String INSERT_ORDER_CERTIFICATES =
            "INSERT INTO certificate_orders(order_id, certificate_id) VALUES (?, ?)";
}
