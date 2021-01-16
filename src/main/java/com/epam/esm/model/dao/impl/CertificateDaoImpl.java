package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    private JdbcTemplate jdbcTemplate;
    private String FIND_ALL_CERTIFICATES_QUEUE = "SELECT id, _name, _description, price, duration, " +
            "create_date, last_update_date";

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Certificate> findAddCertificates() throws DaoException {
        return null;
    }

    @Override
    public Optional<Certificate> findCertificateById(int id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean createCertificate(Certificate certificate) throws DaoException {
        return false;
    }

    @Override
    public boolean updateCertificate(Certificate certificate) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteCertificate(int id) throws DaoException {
        return false;
    }
}
