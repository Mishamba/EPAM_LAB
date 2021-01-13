package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;

import java.util.List;

public class TagDaoImpl implements TagDao {
    @Override
    public List<Tag> findAddTags() throws DaoException {
        return null;
    }

    @Override
    public Tag findTagById(int id) throws DaoException {
        return null;
    }

    @Override
    public boolean createTag(Tag tag) throws DaoException {
        return false;
    }

    @Override
    public boolean updateTag(Tag tag) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteTag(int id) throws DaoException {
        return false;
    }
}
