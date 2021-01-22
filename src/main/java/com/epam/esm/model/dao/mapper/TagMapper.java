package com.epam.esm.model.dao.mapper;

import com.epam.esm.model.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Tag Mapper. Used by JdbcTemplate bean to form Tag from ResultSet.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.dao
 * @see java.sql.ResultSet
 * @see org.springframework.jdbc.core.JdbcTemplate
 * @see org.springframework.jdbc.core.RowMapper
 */
public class TagMapper implements RowMapper<Tag> {

    /**
     * Interface method to get Tag from result set.
     *
     * @param resultSet ResultSet stores certificate info.
     * @param i ResultSet row number.
     * @return com.epam.esm.model.entity.Certificate Certificate assembled from resultSet.
     * @throws SQLException Interface exception.
     */
    @Override
    public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Tag(resultSet.getInt("id"), resultSet.getString("_name"));
    }
}
