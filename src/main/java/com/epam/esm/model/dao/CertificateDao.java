package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.DaoException;

import java.util.List;

public interface CertificateDao {
    List<Certificate> findAllCertificates() throws DaoException;
    Certificate findCertificateById(int id) throws DaoException;
    void createCertificate(Certificate certificate) throws DaoException;
    void updateCertificate(Certificate certificate) throws DaoException;
    void deleteCertificate(int id) throws DaoException;
}
