package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.model.util.entity.PaginationData;
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
    public List<Tag> findAllTags(PaginationData paginationData) throws ServiceException {
        List<Tag> tags= tagDao.findAllTags(paginationData.getPageNumber());
        ifNullThrowServiceException(tags);
        return tags;
    }

    private void ifNullThrowServiceException(List<Tag> certificates) throws ServiceException {
        if (certificates == null) {
            throw new ServiceException("no certificates found", new NullPointerException("list is null"));
        }
    }

    @Override
    public Tag findTagById(int id) {
        return tagDao.findTagById(id);
    }

    @Override
    public void createTag(Tag tag) {
        tagDao.createTag(tag);
    }

    @Override
    public void deleteTag(int id) {
        tagDao.deleteTag(id);
    }
}
