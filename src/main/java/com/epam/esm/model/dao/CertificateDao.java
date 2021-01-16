package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.DaoException;

import java.util.List;

public interface CertificateDao {
    List<Certificate> findAllCertificates() throws DaoException;
    Certificate findCertificateById(int id) throws DaoException;
    boolean createCertificate(Certificate certificate) throws DaoException;
    boolean updateCertificate(Certificate certificate) throws DaoException;
    boolean deleteCertificate(int id) throws DaoException;
}
