package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Tag> findAllTags(int pageNumber) {
        return manager.createQuery("SELECT e FROM tag e", Tag.class).setMaxResults(PageSizeConstant.TAG_PAGE_SIZE).
                setFirstResult(PageSizeConstant.TAG_PAGE_SIZE * pageNumber).getResultList();
    }

    @Override
    public Tag findTagById(int id) {
        return manager.createQuery("SELECT e FROM tag e WHERE e.id = :id", Tag.class).setParameter("id", id).
                getSingleResult();
    }

    @Override
    public Tag findTagByName(String tagName) {
        return manager.createQuery("SELECT e FROM tag e WHERE e.name = :name", Tag.class).setParameter("name", tagName).
                getSingleResult();
    }

    @Override
    @Transactional
    public void createTag(Tag tag) {
        manager.persist(tag);
    }

    @Override
    @Transactional
    public void deleteTag(int id) {
        manager.createQuery("DELETE FROM tag e WHERE e.id = :id").setParameter("id", id).
                executeUpdate();
    }
}
