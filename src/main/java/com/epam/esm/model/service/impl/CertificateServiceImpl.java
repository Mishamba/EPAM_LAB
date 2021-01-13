package com.epam.esm.model.service.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;

import java.util.List;

public class CertificateServiceImpl implements CertificateService {
    @Override
    public List<Certificate> findAllCertificates() throws ServiceException {
        return null;
    }

    @Override
    public Certificate findCertificateById(int id) throws ServiceException {
        return null;
    }

    @Override
    public boolean createCertificate(Certificate certificate) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateCertificate(Certificate certificate) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteCertificate(int id) throws ServiceException {
        return false;
    }
}
