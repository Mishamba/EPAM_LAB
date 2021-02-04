package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.util.comparator.certificate.CertificateComparatorFactory;
import com.epam.esm.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDao certificateDao;
    private final CertificateComparatorFactory certificateComparatorFactory;
    private final Logger logger = Logger.getLogger(CertificateServiceImpl.class);

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao, CertificateComparatorFactory certificateComparatorFactory) {
        this.certificateDao = certificateDao;
        this.certificateComparatorFactory = certificateComparatorFactory;
    }

    @Override
    public List<Certificate> findAllCertificates(PaginationData paginationData) throws ServiceException {
        try {
            List<Certificate> certificates = certificateDao.findAllCertificates(paginationData.getPageNumber());
            sortCertificateList(certificates, paginationData);
            return certificates;
        } catch (DaoException e) {
            logger.error("can't find certificates");
            throw new ServiceException("can't find certificates");
        }
    }

    @Override
    public Certificate findCertificateById(int id) throws ServiceException {
        try {
            return certificateDao.findCertificateById(id);
        } catch (DaoException e) {
            logger.error("can't find certificate by id");
            throw new ServiceException("can't find certificate by id", e);
        }
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName, PaginationData paginationData) throws ServiceException {
        try {
            List<Certificate> certificates = certificateDao.findCertificatesByTag(tagName, paginationData.getPageNumber());
            sortCertificateList(certificates, paginationData);
            return certificates;
        } catch (DaoException e) {
            logger.error("can't find certificate with given tag name");
            throw new ServiceException("can't find certificate with given tag name", e);
        }
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  PaginationData paginationData)
            throws ServiceException {
        if (certificateName == null) {
            certificateName = ModelConstant.STRANGE_SYMBOL;
        }

        if (description == null) {
            description = ModelConstant.STRANGE_SYMBOL;
        }

        try {
            List<Certificate> certificates = certificateDao.findCertificatesByNameAndDescription(certificateName, description,
                    paginationData.getPageNumber());
            sortCertificateList(certificates, paginationData);
            return certificates;
        } catch (DaoException e) {
            logger.error("can't fin certificate with given name and description");
            throw new ServiceException("can't fin certificate with given name and description", e);
        }
    }

    private void sortCertificateList(List<Certificate> certificates, PaginationData paginationData) {
        Comparator<Certificate> certificateComparator = certificateComparatorFactory.getComparator(paginationData);
        certificates.sort(certificateComparator);
    }

    @Override
    public boolean createCertificate(Certificate certificate) throws ServiceException {
        try {
            return certificateDao.createCertificate(certificate);
        } catch (DaoException e) {
            logger.error("can't create certificate");
            throw new ServiceException("can't create certificate");
        }
    }

    @Override
    public boolean updateCertificate(Certificate newCertificate) throws ServiceException {
        try {
            newCertificate.setLastUpdateDate(LocalDateTime.now());
            return certificateDao.updateCertificate(newCertificate);
        } catch (DaoException e) {
            logger.error("can't update certificate");
            throw new ServiceException("can't update certificate", e);
        }
    }

    @Override
    public boolean deleteCertificate(int id) throws ServiceException {
        try {
            return certificateDao.deleteCertificate(id);
        } catch (DaoException e) {
            logger.error("can't delete certificate");
            throw new ServiceException("can't update certificate", e);
        }
    }
}
