package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.mapper.TagMapper;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TagDaoImpl implements TagDao {
    private JdbcTemplate jdbcTemplate;
    private String ALL_TAGS_QUEUE = "SELECT id, _name FROM tag";
    private String TAG_BY_ID_QUEUE = "SELECT id, _name FROM tag WHERE id = ?";
    private String CREATE_TAG_QUEUE = "INSERT INTO tag (_name) VALUE (?)";
    private String DELETE_TAG_FROM_TAG_TABLE = "DELETE FROM tag WHERE id = ?; " +
            "DELETE FROM certificate_tags WHERE id = ?";

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAddTags() throws DaoException {
        try {
            return jdbcTemplate.query(ALL_TAGS_QUEUE, new TagMapper());
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public Tag findTagById(int id) throws DaoException {
        try {
            return jdbcTemplate.query(TAG_BY_ID_QUEUE, new TagMapper(), new Object[]{id}).
                    stream().findAny().orElse(null);
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public boolean createTag(Tag tag) throws DaoException {
        return jdbcTemplate.update(CREATE_TAG_QUEUE, tag.getName()) == 1;
    }

    @Override
    @Transactional
    public boolean deleteTag(int id) throws DaoException {
        return jdbcTemplate.update(DELETE_TAG_FROM_TAG_TABLE, id, id) == 2;
    }
}
