package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Certificate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Certificate> findAllCertificates(int pageNumber) {
        return manager.createQuery("SELECT e FROM Certificate e", Certificate.class).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    public Certificate findCertificateById(int id) {
        return manager.createQuery("SELECT e FROM Certificate e WHERE e.id = :id", Certificate.class).
                setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName, int pageNumber) {
        return manager.
                createQuery("SELECT e FROM Certificate AS c WHERE c.id IN " +
                                "(SELECT gc.id FROM tag t JOIN t.certificates gc WHERE t.name = :name)",
                        Certificate.class).setParameter("name", tagName).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  int pageNumber) {
        return manager.createQuery("SELECT e FROM Certificate WHERE " +
                "e.name REGEXP :nameRegExp OR e.description REGEXP :descriptionRegExp", Certificate.class).
                setParameter("nameRegExp", prepareRegEx(certificateName)).
                setParameter("descriptionRegExp", prepareRegEx(description)).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * pageNumber).
                getResultList();
    }

    private String prepareRegEx(String input) {
        return ".*" + input + ".*";
    }

    @Transactional
    @Override
    public void createCertificate(Certificate certificate) {
        manager.persist(certificate);
    }

    // TODO: 2/8/21 add update duration and price methods

    @Transactional
    @Override
    public void deleteCertificate(int id) {
        manager.createQuery("DELETE FROM Certificate e WHERE e.id = :id").setParameter("id", id);
    }
}
