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
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * (pageNumber - 1)).getResultList();
    }

    @Override
    public Certificate findCertificateById(int id) {
        return manager.createQuery("SELECT e FROM Certificate e WHERE e.id = :id", Certificate.class).
                setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName, int pageNumber) {
        return manager.
                createQuery("SELECT e FROM Certificate e JOIN e.tags tags WHERE EXISTS " +
                                "(SELECT e0 FROM Certificate e0 JOIN e0.tags tags " +
                                "WHERE tags.name = :name AND e0.id =e.id)",
                        Certificate.class).setParameter("name", tagName).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  int pageNumber) {
        String query = "SELECT e FROM Certificate e WHERE e.name LIKE CONCAT('%', :descriptionRegExp,'%') OR e.description " +
                "LIKE CONCAT('%', :nameRegExp, '%')" ;
        return manager.createQuery("SELECT e FROM Certificate e " +
                "WHERE e.name LIKE CONCAT('%', :descriptionRegExp,'%') " +
                "OR e.description LIKE CONCAT('%', :nameRegExp, '%')", Certificate.class).
                setParameter("nameRegExp", certificateName).
                setParameter("descriptionRegExp", prepareRegEx(description)).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * pageNumber).
                getResultList();
    }

    private String prepareRegEx(String input) {
        return "%" + input + "%";
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
        manager.createQuery("DELETE FROM Certificate e WHERE e.id = :id").setParameter("id", id).executeUpdate();
    }
}
