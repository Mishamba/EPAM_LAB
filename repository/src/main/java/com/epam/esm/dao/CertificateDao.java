package com.epam.esm.dao;

import com.epam.esm.model.entity.Certificate;

import java.util.List;

public interface CertificateDao {
    List<Certificate> findAllCertificates(int pageNumber);
    Certificate findCertificateById(int id);
    List<Certificate> findCertificatesByTag(String tagName, int pageNumber);
    List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description, int pageNumber);
    void createCertificate(Certificate certificate);
    void deleteCertificate(int id);
}
