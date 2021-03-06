package com.epam.esm.dao.mapper;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.util.exception.UtilException;
import com.epam.esm.model.util.parser.DateTimeParser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Certificate Mapper. Used by JdbcTemplate bean to form Certificate from ResultSet.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.dao
 * @see java.sql.ResultSet
 * @see org.springframework.jdbc.core.JdbcTemplate
 * @see org.springframework.jdbc.core.RowMapper
 */
public class CertificateWithoutTagsMapper implements RowMapper<Certificate> {
    private final DateTimeParser dateTimeParser;

    /**
     * Constructor.
     * @param dateTimeParser This parser parses create_date data that stored in database as varchar(35).
     */
    public CertificateWithoutTagsMapper(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    /**
     * This method assembles info from ResultSet into Certificate. Output Certificate has no tags info.
     *
     * @param resultSet ResultSet stores certificate info.
     * @param i ResultSet row number.
     * @return com.epam.esm.model.entity.Certificate Certificate assembled from resultSet.
     * @throws SQLException Interface exception.
     */
    @Override
    public Certificate mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            return new Certificate(resultSet.getInt("id"), resultSet.getString("certificate_name"),
                    resultSet.getString("certificate_description"), resultSet.getInt("price"), resultSet.getInt("duration"),
                    dateTimeParser.parseTo(resultSet.getString("create_date")),
                    dateTimeParser.parseTo(resultSet.getString("last_update_date")), null);
        } catch (UtilException exception) {
            return null;
        }
    }
}
