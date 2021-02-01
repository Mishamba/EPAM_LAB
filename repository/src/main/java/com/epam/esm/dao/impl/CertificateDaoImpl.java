package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.CertificateWithoutTagsMapper;
import com.epam.esm.dao.mapper.IntegerMapper;
import com.epam.esm.dao.queue.CertificateQueryRepository;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
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
import java.util.Objects;
import java.util.Optional;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    private final Logger logger = Logger.getLogger(CertificateDaoImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final TagDao tagDao;
    private final DateTimeParser dateTimeParser;

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao, DateTimeParser dateTimeParser) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
        this.dateTimeParser = dateTimeParser;
    }

    @Override
    public List<Certificate> findAllCertificates() throws DaoException {
        List<Certificate> certificates = findAllCertificatesFromDB();
        for (Certificate certificate : certificates) {
            setCertificateTag(certificate);
        }
        return certificates;
    }

    private List<Certificate> findAllCertificatesFromDB() throws DaoException {
        try {
            return jdbcTemplate.query(CertificateQueryRepository.ALL_CERTIFICATES_QUEUE,
                    new CertificateWithoutTagsMapper(dateTimeParser));
        } catch (DataAccessException exception) {
            logger.error("can't get data");
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public Certificate findCertificateById(int id) throws DaoException {
        Optional<Certificate> optionalCertificate = findCertificateByIdFromDB(id);
        if (optionalCertificate.isPresent()) {
            Certificate certificate = optionalCertificate.get();
            setCertificateTag(certificate);
            return certificate;
        } else {
            logger.error("invalid id given");
            throw new DaoException("no such id");
        }
    }

    @Override
    public List<Certificate> findCertificatesByTag(String tagName) throws DaoException {
        try {
            return jdbcTemplate.query(CertificateQueryRepository.CERTIFICATES_BY_TAG_NAME,
                    new CertificateWithoutTagsMapper(dateTimeParser), tagName);
        } catch (DataAccessException exception) {
            logger.error("can't get data");
            throw new DaoException("can't get data", exception);
        }
    }

    @Override
    public List<Certificate> findCertificatesByNameAndDescription(String certificateName, String description)
            throws DaoException {
        String certificateNameRegEx = prepareRegEx(certificateName);
        String descriptionRegEx = prepareRegEx(description);
        try {
            return jdbcTemplate.query(CertificateQueryRepository.CERTIFICATE_BY_NAME_AND_DESCRIPTION_PART,
                    new CertificateWithoutTagsMapper(dateTimeParser), certificateNameRegEx, descriptionRegEx);
        } catch (DataAccessException exception) {
            logger.error("can't get data");
            throw new DaoException("can't get data", exception);
        }
    }

    private String prepareRegEx(String input) {
        return ".*" + input + ".*";
    }

    private Optional<Certificate> findCertificateByIdFromDB(int id) throws DaoException {
        try {
            return jdbcTemplate.query(CertificateQueryRepository.CERTIFICATE_BY_ID_QUEUE,
                    new CertificateWithoutTagsMapper(dateTimeParser), id).
                    stream().findAny();
        } catch (DataAccessException exception) {
            logger.error("can't get data");
            throw new DaoException("can't get data", exception);
        }
    }

    private void setCertificateTag(Certificate certificate) throws DaoException {
        List<Integer> tagIdList = jdbcTemplate.query(CertificateQueryRepository.CERTIFICATE_TAGS_ID_QUEUE, new IntegerMapper(),
                certificate.getId());
        for (int tagId : tagIdList) {
            certificate.addTag(tagDao.findTagById(tagId));
        }
    }

    @Transactional
    @Override
    public boolean createCertificate(Certificate certificate) throws DaoException {
        Integer generatedId = createCertificateInDB(certificate);
        certificate.setId(generatedId);
        return createCertificateTagsReferences(certificate);
    }

    private Integer createCertificateInDB(Certificate certificate) throws DaoException {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreatorFactory preparedStatementCreatorFactory =
                new PreparedStatementCreatorFactory(CertificateQueryRepository.CREATE_CERTIFICATE_QUEUE, Types.VARCHAR,
                        Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.VARCHAR, Types.VARCHAR);

        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator preparedStatementCreator;
        try {
            preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
                    Arrays.asList(certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                            certificate.getDuration(),dateTimeParser.parseFrom(certificate.getCreateDate()), dateTimeParser.parseFrom(certificate.getLastUpdateDate()))
            );
        } catch (UtilException e) {
            logger.error("can't parse date");
            throw new DaoException("query creation error", e);
        }

        jdbcTemplate.update(preparedStatementCreator, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    private boolean createCertificateTagsReferences(Certificate certificate) throws DaoException {
        int affectedRows = 0;
        for (Tag tag : certificate.getTags()) {
            try {
                affectedRows += jdbcTemplate.update(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE,
                        certificate.getId(), tag.getId());
            } catch (DataAccessException exception) {
                logger.info("creating new tag while certificate creation");
                tagDao.createTag(tag);
                tag.setId(tagDao.findTagByName(tag.getName()).getId());
                jdbcTemplate.update(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE,
                        certificate.getId(), tag.getId());
            }
        }

        return affectedRows == certificate.getTags().size();
    }

    @Transactional
    @Override
    public boolean updateCertificate(Certificate certificate) throws DaoException {
        try {
            updateCertificateTagsInDB(certificate);
            return updateCertificateInDB(certificate);
        } catch (DataAccessException exception) {
            logger.error("can't update certificate in database");
            throw new DaoException("can't update certificate in database", exception);
        }
    }

    private void updateCertificateTagsInDB(Certificate certificate) {
        jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, certificate.getId());
        for (Tag tag : certificate.getTags()) {
            jdbcTemplate.update(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE,
                    certificate.getId(), tag.getId());
        }
    }

    private boolean updateCertificateInDB(Certificate certificate) throws DaoException {
        try {
            return jdbcTemplate.update(CertificateQueryRepository.UPDATE_CERTIFICATE_BY_ID_QUEUE, certificate.getName(),
                    certificate.getDescription(), certificate.getPrice(), certificate.getDuration(),
                    dateTimeParser.parseFrom(certificate.getLastUpdateDate()), certificate.getId()) == 1;
        } catch (UtilException e) {
            logger.error("parse error");
            throw new DaoException("query execute error", e);
        }
    }

    @Transactional
    @Override
    public boolean deleteCertificate(int id) throws DaoException {
        try {
            jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, id);
            return jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_BY_ID_QUEUE, id) == 1;
        } catch (DataAccessException exception) {
            logger.error("couldn't delete certificate");
            throw new DaoException("can't delete certificate");
        }
    }
}
