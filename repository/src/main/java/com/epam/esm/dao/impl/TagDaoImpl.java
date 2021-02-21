package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Tag> findAllTags(int pageNumber) {
        return manager.createQuery("SELECT DISTINCT e FROM Tag e", Tag.class).setMaxResults(PageSizeConstant.TAG_PAGE_SIZE).
                setFirstResult(PageSizeConstant.TAG_PAGE_SIZE * (pageNumber - 1)).getResultList();
    }

    @Override
    public Tag findTagById(int id) {
        return manager.createQuery("SELECT DISTINCT e FROM Tag e WHERE e.id = :id", Tag.class).setParameter("id", id).
                getSingleResult();
    }

    @Override
    public Tag findTagByName(String tagName) {
        return manager.createQuery("SELECT DISTINCT e FROM Tag e WHERE e.name = :name", Tag.class).
                setParameter("name", tagName).getResultList().stream().findAny().orElse(null);
    }

    @Override
    @Transactional
    public void createTag(Tag tag) {
        manager.persist(tag);
    }

    @Override
    @Transactional
    public void deleteTag(int id) {
        Tag tagToDelete = getTagWithCertificatesById(id);

        removeCertificateTagReferences(tagToDelete);

        manager.remove(tagToDelete);
    }

    private Tag getTagWithCertificatesById(int id) {
        return manager.
                createQuery("SELECT DISTINCT e FROM Tag e JOIN fetch e.certificates WHERE e.id = :id", Tag.class).
                setParameter("id", id).
                getSingleResult();
    }

    // Give tags with fetched certificates.
    private void removeCertificateTagReferences(Tag tag) {
        for (Certificate certificate : tag.getCertificates()) {
            List<Tag> tagList = certificate.getTags();
            tagList.remove(tag);
            certificate.setTags(tagList);
            manager.persist(certificate);
        }

        tag.setCertificates(new ArrayList<>());
    }
}
