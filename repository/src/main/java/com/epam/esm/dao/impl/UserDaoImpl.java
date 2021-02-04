package com.epam.esm.dao.impl;

import com.epam.esm.dao.PageCalculator;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.mapper.UserMapper;
import com.epam.esm.dao.queue.UserQueryRepository;
import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.constant.PageSizeConstant;
import com.epam.esm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends PageCalculator implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findAllUsers(int pageNumber) throws DaoException {
        try {
            return jdbcTemplate.query(UserQueryRepository.SELECT_ALL_USERS_QUERY_PAGED, new UserMapper(),
                    calculatePageStart(pageNumber, PageSizeConstant.USER_PAGE_SIZE),
                    calculatePageEnd(pageNumber, PageSizeConstant.USER_PAGE_SIZE));
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public User findById(int id) throws DaoException {
        try {
            return jdbcTemplate.query(UserQueryRepository.SELECT_USER_BY_ID_QUERY, new UserMapper(), id).stream().
                    findAny().orElse(null);
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }
}
