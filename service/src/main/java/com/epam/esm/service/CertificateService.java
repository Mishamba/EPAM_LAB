package com.epam.esm.service;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CertificateService {
    List<Certificate> findAllCertificates(PaginationData paginationData) throws ServiceException;
    Certificate findCertificateById(int id) throws ServiceException;
    List<Certificate> findCertificatesByTag(String tagName, PaginationData paginationData) throws ServiceException;
    List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                           PaginationData paginationData)
            throws ServiceException;
    boolean createCertificate(Certificate certificate) throws ServiceException;
    boolean updateCertificate(Certificate certificate) throws ServiceException;
    boolean deleteCertificate(int id) throws ServiceException;
}
