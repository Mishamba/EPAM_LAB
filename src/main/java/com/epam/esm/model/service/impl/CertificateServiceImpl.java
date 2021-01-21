package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.DaoException;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateDao certificateDao;
    private final Logger logger = Logger.getLogger(CertificateServiceImpl.class);

    @Autowired
    public CertificateServiceImpl(CertificateDao certificateDao) {
        this.certificateDao = certificateDao;
    }

    @Override
    public List<Certificate> findAllCertificates() throws ServiceException {
        try {
            return certificateDao.findAllCertificates();
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
