package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    private final TagDao tagDao;
    @PersistenceContext
    private EntityManager manager;

    @Autowired
    public CertificateDaoImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Certificate> findAllCertificates(int pageNumber) {
        return manager.createQuery("SELECT DISTINCT e FROM Certificate e", Certificate.class).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * (pageNumber - 1)).getResultList();
    }

    @Override
    public Certificate findCertificateById(int id) {
        return manager.createQuery("SELECT DISTINCT e FROM Certificate e WHERE e.id = :id", Certificate.class).
                setParameter("id", id).getResultList().stream().findAny().orElse(null);
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName, int pageNumber) {
        return manager.
                createQuery("SELECT DISTINCT e FROM Certificate e JOIN e.tags tags WHERE EXISTS " +
                                "(SELECT e0 FROM Certificate e0 JOIN e0.tags tags " +
                                "WHERE tags.name = :name AND e0.id =e.id)",
                        Certificate.class).setParameter("name", tagName).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * (pageNumber - 1)).getResultList();
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description,
                                                                  int pageNumber) {
        return manager.createQuery("SELECT DISTINCT e FROM Certificate e " +
                "WHERE e.name LIKE :nameRegExp " +
                "OR e.description LIKE :descriptionRegExp", Certificate.class).
                setParameter("nameRegExp", prepareRegEx(certificateName)).
                setParameter("descriptionRegExp", prepareRegEx(description)).
                setMaxResults(PageSizeConstant.CERTIFICATE_PAGE_SIZE).
                setFirstResult(PageSizeConstant.CERTIFICATE_PAGE_SIZE * (pageNumber - 1)).
                getResultList();
    }

    @Transactional
    @Override
    public void updateCertificateDuration(int id, int newDuration) {
        manager.createQuery("UPDATE Certificate e SET e.duration = :newDuration").
                setParameter("newDuration", newDuration).executeUpdate();
    }

    @Transactional
    @Override
    public void updateCertificatePrice(int id, int newPrice) {
        manager.createQuery("UPDATE Certificate e SET e.price = :newPrice").
                setParameter("newPrice", newPrice).executeUpdate();
    }

    private String prepareRegEx(String input) {
        return "%" + input + "%";
    }

    @Transactional
    @Override
    public void createCertificate(Certificate certificate) {
        for (Tag tag : certificate.getTags()) {
            if (tagDao.findTagByName(tag.getName()) == null) {
                tagDao.createTag(tag);
            }
        }
        manager.persist(certificate);
    }

    @Transactional
    @Override
    public void deleteCertificate(int id) {
        Certificate certificate = getCertificateWithOrders(id);
        certificate.setTags(getCertificateTagsWithCertificates(certificate));
        removeCertificateOrderReferences(certificate);
        removeCertificateTagReferences(certificate);
        manager.remove(certificate);
    }

    private Certificate getCertificateWithOrders(int id) {
        return manager.
                createQuery("SELECT e FROM Certificate e " +
                        "JOIN FETCH e.orders " +
                        "WHERE e.id = :id",
                        Certificate.class).
                setParameter("id", id).
                getResultList().stream().findAny().orElse(new Certificate());
    }

    private List<Tag> getCertificateTagsWithCertificates(Certificate certificate) {
        List<Tag> tagsWithCertificates = new ArrayList<>();
        for (Tag tag : certificate.getTags()) {
            Tag tagWithCertificates = manager.
                    createQuery("SELECT e FROM Tag e JOIN e.certificates WHERE e.id = :id", Tag.class).
                    setParameter("id", tag.getId()).
                    getSingleResult();
            tagsWithCertificates.add(tagWithCertificates);
        }

        return tagsWithCertificates;
    }

    // Give certificate with fetched orders.
    private void removeCertificateOrderReferences(Certificate certificate) {
        for (Order order : certificate.getOrders()) {
            List<Certificate> orderedCertificates = order.getOrderedCertificates();
            orderedCertificates.remove(certificate);
            order.setOrderedCertificates(orderedCertificates);
            manager.persist(order);
        }

        certificate.setOrders(new ArrayList<>());
    }

    // Give certificate with fetched tags.certificates
    private void removeCertificateTagReferences(Certificate certificate) {
        for (Tag tag : certificate.getTags()) {
            List<Certificate> certificates = tag.getCertificates();
            certificates.remove(certificate);
            tag.setCertificates(certificates);
            manager.persist(tag);
        }

        certificate.setTags(new ArrayList<>());
    }
}
