package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TagDao {
    List<Tag> findAddTags() throws DaoException;
    Tag findTagById(int id) throws DaoException;
    boolean createTag(Tag tag) throws DaoException;
    boolean updateTag(Tag tag) throws DaoException;
    boolean deleteTag(int id) throws DaoException;
}
