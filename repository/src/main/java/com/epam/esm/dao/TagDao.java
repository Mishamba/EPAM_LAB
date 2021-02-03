package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.dao.exception.DaoException;

import java.util.List;

public interface TagDao {
    List<Tag> findAllTags(int pageNumber) throws DaoException;
    Tag findTagById(int id) throws DaoException;
    Tag findTagByName(String tagName) throws DaoException;
    boolean createTag(Tag tag) throws DaoException;
    boolean deleteTag(int id) throws DaoException;
}
