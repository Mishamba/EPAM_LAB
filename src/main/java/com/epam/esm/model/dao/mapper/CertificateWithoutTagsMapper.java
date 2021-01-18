package com.epam.esm.model.dao.mapper;

import com.epam.esm.model.entity.Certificate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CertificateWithoutTagsMapper implements RowMapper<Certificate> {
    // TODO: 1/16/21 add parser and remove getDate methods
    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Certificate(resultSet.getInt("id") ,resultSet.getString("_name"),
                resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("duration"),
                LocalDateTime.parse(resultSet.getString("create_date")),
                LocalDateTime.parse(resultSet.getString("last_update_date")), null);
    }
}
