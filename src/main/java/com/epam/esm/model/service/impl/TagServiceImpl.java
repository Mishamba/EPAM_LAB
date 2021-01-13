package com.epam.esm.model.service.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.TagService;

import java.util.List;

public class TagServiceImpl implements TagService {
    @Override
    public List<Tag> findAllTags() throws ServiceException {
        return null;
    }

    @Override
    public Tag findTagById(int id) throws ServiceException {
        return null;
    }

    @Override
    public boolean createTag(Tag tag) throws ServiceException {
        return false;
    }

    @Override
    public boolean updateTag(Tag tag) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteTag(int id) throws ServiceException {
        return false;
    }
}
