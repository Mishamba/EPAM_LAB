package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final Logger logger = Logger.getLogger(TagServiceImpl.class);

    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<Tag> findAllTags(int pageNumber) throws ServiceException {
        if (pageNumber < 1) {
            logger.error("given non positive number");
            throw new ServiceException("page number must be positive");
        }

        try {
            return tagDao.findAllTags(pageNumber);
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
