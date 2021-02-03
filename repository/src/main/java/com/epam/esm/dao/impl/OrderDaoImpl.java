package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.PageCalculator;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.mapper.IntegerMapper;
import com.epam.esm.dao.mapper.OrderWithoutCertificatesMapper;
import com.epam.esm.dao.queue.OrderQueryRepository;
import com.epam.esm.model.constant.Constant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.util.exception.UtilException;
import com.epam.esm.model.util.parser.DateTimeParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Types;
import java.util.Arrays;
import java.util.List;

@Repository
public class OrderDaoImpl extends PageCalculator implements OrderDao {
    private final Logger logger = Logger.getLogger(OrderDaoImpl.class);
    private final CertificateDao certificateDao;
    private final JdbcTemplate jdbcTemplate;
    private final DateTimeParser parser;

    @Autowired
    public OrderDaoImpl(CertificateDao certificateDao, JdbcTemplate jdbcTemplate, DateTimeParser parser) {
        this.certificateDao = certificateDao;
        this.jdbcTemplate = jdbcTemplate;
        this.parser = parser;
    }

    @Override
    public List<Order> getUserOrders(int userId, int pageNumber) throws DaoException {
        List<Order> orders = getOrdersWithoutCertificates(userId, pageNumber);
        for (Order order : orders) {
            setOrderCertificates(order);
        }

        return orders;
    }

    private List<Order> getOrdersWithoutCertificates(int userId, int pageNumber) throws DaoException {
        try {
            return jdbcTemplate.query(OrderQueryRepository.SELECT_ORDERS_BY_USERS_ID,
                    new OrderWithoutCertificatesMapper(parser), userId,
                    calculatePageStart(pageNumber, Constant.ORDER_PAGE_SIZE),
                    calculatePageEnd(pageNumber, Constant.ORDER_PAGE_SIZE));
        } catch (DataAccessException exception) {
            throw new DaoException("can't get data", exception);
        }
    }

    private void setOrderCertificates(Order order) throws DaoException {
        List<Integer> certificatesIds = jdbcTemplate.query(OrderQueryRepository.SELECT_ORDERS_CERTIFICATES_IDS,
                new IntegerMapper(), order.getId());
        for (Integer certificatesId : certificatesIds) {
            Certificate certificate = certificateDao.findCertificateById(certificatesId);
            order.addOrderedCertificate(certificate);
        }
    }

    @Override
    @Transactional
    public boolean createOrder(Order order) throws DaoException {
        Integer newOrderId = createOrderInDB(order);
        order.setId(newOrderId);
        return createOrderCertificatesReferences(order);
    }

    private Integer createOrderInDB(Order order) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreatorFactory preparedStatementCreatorFactory =
                new PreparedStatementCreatorFactory(OrderQueryRepository.CREATE_ORDER_SIGN,
                        Types.INTEGER, Types.INTEGER);

        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator preparedStatementCreator;
        try {
            preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                    Arrays.asList(order.getUserId(), parser.parseFrom(order.getOrderDate())));
        } catch (UtilException e) {
            logger.error("can't parse date");
            throw new DaoException("query creation error", e);
        }

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return (Integer) keyHolder.getKey();
    }

    private boolean createOrderCertificatesReferences(Order order) {
        int insertedRows = 0;
        for (Certificate orderedCertificate : order.getOrderedCertificates()) {
            insertedRows += jdbcTemplate.update(OrderQueryRepository.INSERT_ORDER_CERTIFICATES,
                    order.getId(), orderedCertificate.getId());
        }

        return insertedRows == order.getOrderedCertificates().size();
    }
}
