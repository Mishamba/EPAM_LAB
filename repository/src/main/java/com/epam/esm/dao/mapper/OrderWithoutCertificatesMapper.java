package com.epam.esm.dao.mapper;

import com.epam.esm.model.entity.Order;
import com.epam.esm.util.exception.UtilException;
import com.epam.esm.util.parser.DateTimeParser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderWithoutCertificatesMapper implements RowMapper<Order> {
    private final DateTimeParser parser;

    public OrderWithoutCertificatesMapper(DateTimeParser parser) {
        this.parser = parser;
    }

    @Override
    public Order mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            return new Order(resultSet.getInt("id"), resultSet.getInt("user_id"), parser.parseTo(resultSet.getString("order_date")));
        } catch (UtilException e) {
            throw new SQLException("can't map Order", e);
        }
    }
}
