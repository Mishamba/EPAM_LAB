package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.dto.CertificateDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.model.util.comparator.certificate.CertificateComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<CertificateDTO> findAllCertificates(PaginationData paginationData) throws ServiceException {
        List<Certificate> certificates = certificateDao.findAllCertificates(paginationData.getPageNumber());

        ifEmptyOrNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);

        return covertToDTO(certificates);
    }

    @Override
    public CertificateDTO findCertificateById(int id) {
        return CertificateDTO.createFromCertificate(certificateDao.findCertificateById(id));
    }

    @Override
    public List<CertificateDTO> findCertificatesByTag(String tagName, PaginationData paginationData) throws ServiceException {
        List<Certificate> certificates = certificateDao.findCertificatesByTag(tagName, paginationData.getPageNumber());

        ifEmptyOrNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);

        return covertToDTO(certificates);
    }

    @Override
    public List<CertificateDTO> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  PaginationData paginationData) throws ServiceException {
        if (certificateName == null) {
            certificateName = ModelConstant.STRANGE_SYMBOL;
        }

        if (description == null) {
            description = ModelConstant.STRANGE_SYMBOL;
        }

        List<Certificate> certificates = certificateDao.findCertificatesByNameAndDescription(certificateName, description,
                paginationData.getPageNumber());

        ifEmptyOrNullThrowServiceException(certificates);

        sortCertificateList(certificates, paginationData);

        return covertToDTO(certificates);
    }

    private List<CertificateDTO> covertToDTO(List<Certificate> certificates) {
        return certificates.stream().map(CertificateDTO::createFromCertificate).collect(Collectors.toList());
    }

    private void sortCertificateList(List<Certificate> certificates, PaginationData paginationData) {
        Comparator<Certificate> certificateComparator = certificateComparatorFactory.getComparator(paginationData);
        certificates.sort(certificateComparator);
    }

    private void ifEmptyOrNullThrowServiceException(List<Certificate> certificates) throws ServiceException {
        if (certificates == null || certificates.isEmpty()) {
            throw new ServiceException("no certificates found", new NullPointerException("list is null"));
        }
    }

    @Override
    public void createCertificate(Certificate certificate) {
        certificateDao.createCertificate(certificate);
    }

    @Override
    public void updateCertificateDuration(int id, int duration) {
        certificateDao.updateCertificateDuration(id, duration);
    }

    @Override
    public void updateCertificatePrice(int id, int price) {
        certificateDao.updateCertificatePrice(id, price);
    }

    @Override
    public void deleteCertificate(int id) {
        certificateDao.deleteCertificate(id);
    }
}
