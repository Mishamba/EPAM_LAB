package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.mapper.TagMapper;
import com.epam.esm.model.dao.queue.TagQueryRepository;
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
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAllTags() throws DaoException {
        try {
            return jdbcTemplate.query(TagQueryRepository.ALL_TAGS_QUEUE, new TagMapper());
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public Tag findTagById(int id) throws DaoException {
        try {
            return jdbcTemplate.query(TagQueryRepository.TAG_BY_ID_QUEUE, new TagMapper(), new Object[]{id}).
                    stream().findAny().orElse(null);
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public boolean createTag(Tag tag) throws DaoException {
        try {
            return jdbcTemplate.update(TagQueryRepository.CREATE_TAG_QUEUE, tag.getName()) == 1;
        } catch (DataAccessException exception) {
            throw new DaoException("data insert error", exception);
        }
    }

    @Override
    @Transactional
    public boolean deleteTag(int id) throws DaoException {
        try {
            return jdbcTemplate.update(TagQueryRepository.DELETE_TAG_FROM_TAG_TABLE, id, id) >= 1;
        } catch (DataAccessException exception) {
            throw new DaoException("data deletion error", exception);
        }
    }
}
