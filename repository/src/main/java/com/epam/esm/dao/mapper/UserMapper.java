package com.epam.esm.dao.mapper;

import com.epam.esm.dao.PageCalculator;
import com.epam.esm.model.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return new User(resultSet.getInt("id"), resultSet.getString("user_name"));
    }
}
