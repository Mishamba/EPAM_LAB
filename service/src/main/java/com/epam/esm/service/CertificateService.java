package com.epam.esm.service;

import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CertificateService {
    List<Certificate> findAllCertificates(int pageNumber) throws ServiceException;
    Certificate findCertificateById(int id) throws ServiceException;
    List<Certificate> findCertificatesByTag(String tagName, int pageNumber) throws ServiceException;
    List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description, int pageNumber)
            throws ServiceException;
    boolean createCertificate(Certificate certificate) throws ServiceException;
    boolean updateCertificate(Certificate certificate) throws ServiceException;
    boolean deleteCertificate(int id) throws ServiceException;
}
