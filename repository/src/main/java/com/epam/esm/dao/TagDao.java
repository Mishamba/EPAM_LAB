package com.epam.esm.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;

public interface TagDao {
    List<Tag> findAllTags(int pageNumber);
    Tag findTagById(int id);
    Tag findTagByName(String tagName);
    void createTag(Tag tag);
    void deleteTag(int id);
}
