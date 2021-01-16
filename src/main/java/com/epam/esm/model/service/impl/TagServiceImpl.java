package com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagDao tagDao;
    private final static Logger logger = Logger.getLogger(TagServiceImpl.class);

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAllTags() throws ServiceException {
        try {
            return tagDao.findAllTags();
        } catch (DaoException e) {
            logger.error("can't get tags");
            throw new ServiceException("can't get tags", e);
        }
    }

    @Override
    public Tag findTagById(int id) throws ServiceException {
        try {
            return tagDao.findTagById(id);
        } catch (DaoException e) {
            logger.error("can't get tag by id");
            throw new ServiceException("can't get tag by id", e);
        }
    }

    @Override
    public boolean createTag(Tag tag) throws ServiceException {
        try {
            return tagDao.createTag(tag);
        } catch (DaoException e) {
            logger.error("can't create tag");
            throw new ServiceException("can't create tag", e);
        }
    }

    @Override
    public boolean deleteTag(int id) throws ServiceException {
        try {
            return tagDao.deleteTag(id);
        } catch (DaoException e) {
            logger.error("can't delete tag");
            throw new ServiceException("can't delete tag", e);
        }
    }
}
