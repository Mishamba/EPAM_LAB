package com.epam.esm.model.dao.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Integer Mapper. Used by JdbcTemplate bean to get integer from ResultSet.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.dao
 * @see java.sql.ResultSet
 * @see org.springframework.jdbc.core.JdbcTemplate
 * @see org.springframework.jdbc.core.RowMapper
 */
public class IntegerMapper implements RowMapper<Integer> {
    /**
     * Interface method to get integer from result set. Used to get id.
     *
     * @param resultSet ResultSet stores certificate info.
     * @param i ResultSet row number.
     * @return com.epam.esm.model.entity.Certificate Certificate assembled from resultSet.
     * @throws SQLException Interface exception.
     */
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt(1);
    }
}
