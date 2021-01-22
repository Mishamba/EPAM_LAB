package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.mapper.CertificateWithoutTagsMapper;
import com.epam.esm.model.dao.mapper.IntegerMapper;
import com.epam.esm.model.dao.queue.CertificateQueryRepository;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
import com.epam.esm.model.exception.UtilException;
import com.epam.esm.util.parser.DateTimeParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateDaoImpl implements CertificateDao {
    private Logger logger = Logger.getLogger(CertificateDaoImpl.class);
    private JdbcTemplate jdbcTemplate;
    private TagDao tagDao;
    private DateTimeParser dateTimeParser;

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

    // TODO: 1/16/21 check this code
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
        if (generatedId == null) {
            logger.error("couldn't create certificate");
            throw new DaoException("can't create certificate");
        }
            certificate.setId(generatedId);
        return createCertificateTagsReferences(certificate);
    }

    private Integer createCertificateInDB(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CertificateQueryRepository.CREATE_CERTIFICATE_QUEUE);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setInt(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            try {
                ps.setString(5, dateTimeParser.parseFrom(certificate.getCreateDate()));
                ps.setString(6, dateTimeParser.parseFrom(certificate.getLastUpdateDate()));
            } catch (UtilException exception) {
                logger.error("can't parse certificate dateTime");
            }

            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Integer.TYPE);
    }

    private boolean createCertificateTagsReferences(Certificate certificate) {
        int affectedRows = 0;
        for (Tag tag : certificate.getTags()) {
            affectedRows += jdbcTemplate.update(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE,
                    certificate.getId(), tag.getId());
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

    private boolean updateCertificateInDB(Certificate certificate) {
        return jdbcTemplate.update(CertificateQueryRepository.UPDATE_CERTIFICATE_BY_ID_QUEUE, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getLastUpdateDate(),
                certificate.getId()) == 1;
    }

    private void updateCertificateTagsInDB(Certificate certificate) {
        jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, certificate.getId());
        for (Tag tag : certificate.getTags()) {
            jdbcTemplate.update(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE,
                    certificate.getId(), tag.getId());
        }
    }

    @Transactional
    @Override
    public boolean deleteCertificate(int id) throws DaoException {
        try {
            return jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, id) == 1 &&
                    jdbcTemplate.update(CertificateQueryRepository.DELETE_CERTIFICATE_BY_ID_QUEUE, id) >= 0;
        } catch (DataAccessException exception) {
            logger.error("couldn't delete certificate");
            throw new DaoException("can't delete certificate");
        }
    }
}
