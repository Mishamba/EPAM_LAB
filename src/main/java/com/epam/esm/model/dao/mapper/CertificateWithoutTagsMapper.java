package com.epam.esm.model.dao.mapper;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.parser.DateTimeParser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CertificateWithoutTagsMapper implements RowMapper<Certificate> {
    private final DateTimeParser dateTimeParser;

    public CertificateWithoutTagsMapper(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Certificate(resultSet.getInt("id") ,resultSet.getString("_name"),
                resultSet.getString("description"), resultSet.getInt("price"), resultSet.getInt("duration"),
                dateTimeParser.parseTo(resultSet.getString("create_date")),
                dateTimeParser.parseTo(resultSet.getString("last_update_date")), null);
    }
}
