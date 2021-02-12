package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.model.util.comparator.certificate.CertificateComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDao certificateDao;
    private final CertificateComparatorFactory certificateComparatorFactory;

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, CertificateComparatorFactory certificateComparatorFactory) {
        this.certificateDao = certificateDao;
        this.certificateComparatorFactory = certificateComparatorFactory;
    }

    @Override
    public List<Certificate> findAllCertificates(PaginationData paginationData) throws ServiceException {
        List<Certificate> certificates = certificateDao.findAllCertificates(paginationData.getPageNumber());

        ifNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);
        return certificates;
    }

    @Override
    public Certificate findCertificateById(int id) {
        return certificateDao.findCertificateById(id);
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName, PaginationData paginationData) throws ServiceException {
        List<Certificate> certificates = certificateDao.findCertificatesByTag(tagName, paginationData.getPageNumber());

        ifNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);
        return certificates;
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  PaginationData paginationData) throws ServiceException {
        if (certificateName == null) {
            certificateName = ModelConstant.STRANGE_SYMBOL;
        }

        if (description == null) {
            description = ModelConstant.STRANGE_SYMBOL;
        }

        List<Certificate> certificates = certificateDao.findCertificatesByNameAndDescription(certificateName, description,
                paginationData.getPageNumber());

        ifNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);
        return certificates;
    }

    private void sortCertificateList(List<Certificate> certificates, PaginationData paginationData) {
        Comparator<Certificate> certificateComparator = certificateComparatorFactory.getComparator(paginationData);
        certificates.sort(certificateComparator);
    }

    private void ifNullThrowServiceException(List<Certificate> certificates) throws ServiceException {
        if (certificates == null) {
            throw new ServiceException("no certificates found", new NullPointerException("list is null"));
        }
    }

    @Override
    public void createCertificate(Certificate certificate) {
        certificateDao.createCertificate(certificate);
    }

    /*@Override
    public void updateCertificate(Certificate newCertificate) {
        newCertificate.setLastUpdateDate(LocalDateTime.now());
        return certificateDao.updateCertificate(newCertificate);
    }*/

    @Override
    public void deleteCertificate(int id) {
        certificateDao.deleteCertificate(id);
    }
}
