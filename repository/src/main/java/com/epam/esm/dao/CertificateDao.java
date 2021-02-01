package com.epam.esm.dao;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.dao.exception.DaoException;

import java.util.List;

public interface CertificateDao {
    List<Certificate> findAllCertificates() throws DaoException;
    Certificate findCertificateById(int id) throws DaoException;
    List<Certificate> findCertificatesByTag(String tagName) throws DaoException;
    List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description)
            throws DaoException;
    boolean createCertificate(Certificate certificate) throws DaoException;
    boolean updateCertificate(Certificate certificate) throws DaoException;
    boolean deleteCertificate(int id) throws DaoException;
}
