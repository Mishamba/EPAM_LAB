package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class CertificateDaoImpl implements CertificateDao {
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
