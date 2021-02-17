package com.epam.esm.service;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.dto.CertificateDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CertificateService {
    List<CertificateDTO> findAllCertificates(PaginationData paginationData) throws ServiceException;
    CertificateDTO findCertificateById(int id);
    List<CertificateDTO> findCertificatesByTag(String tagName, PaginationData paginationData) throws ServiceException;
    List<CertificateDTO> findCertificatesByNameAndDescription(
            String certificateName, String description, PaginationData paginationData) throws ServiceException;
    void createCertificate(Certificate certificate);
    void updateCertificateDuration(int id, int duration);
    void updateCertificatePrice(int id, int price);
    void deleteCertificate(int id);
}
