package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.mapper.CertificateWithoutTagsMapper;
import com.epam.esm.model.dao.mapper.IntegerMapper;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
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
    private final String ALL_CERTIFICATES_QUEUE = "SELECT id, _name, _description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate";
    private final String CERTIFICATE_TAGS_ID_QUEUE = "SELECT tag_id FROM tag_tags WHERE certificate_id = ?";
    private final String CERTIFICATE_BY_ID_QUEUE = "SELECT id, _name, _description, price, duration, " +
            "create_date, last_update_date FROM gift_certificate WHERE id = ?";
    private final String CREATE_CERTIFICATE_QUEUE = "INSERT INTO gift_certificate " +
            "(_name, _description, price, duration, create_date, last_update_date) value (?,?,?,?,?,?)";
    private final String CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE = "INSERT INTO certificate_tags " +
            "(certificate_id, tag_id) value (?,?)";
    private final String DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE = "DELETE FROM certificate_tags " +
            "WHERE certificate_id = ?";
    private final String DELETE_CERTIFICATE_BY_ID_QUEUE = "DELETE FROM gift_certificate WHERE id = ?";
    private final String UPDATE_CERTIFICATE_BY_ID_QUEUE = "UPDATE gift_certificate SET _name = ? , _description = ? , " +
            "price = ? , duration = ? , last_update_date = ? WHERE id = ?";

    @Autowired
    public CertificateDaoImpl(JdbcTemplate jdbcTemplate, TagDao tagDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagDao = tagDao;
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
            return jdbcTemplate.query(ALL_CERTIFICATES_QUEUE, new CertificateWithoutTagsMapper());
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
            return jdbcTemplate.query(CERTIFICATE_BY_ID_QUEUE, new CertificateWithoutTagsMapper(), id).
                    stream().findAny();
        } catch (DataAccessException exception) {
            logger.error("can't get data");
            throw new DaoException("can't get data", exception);
        }
    }

    private void setCertificateTag(Certificate certificate) throws DaoException {
        List<Integer> tagIdList = jdbcTemplate.query(CERTIFICATE_TAGS_ID_QUEUE, new IntegerMapper(),
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

    // This method returns generated id in case of success creation
    private Integer createCertificateInDB(Certificate certificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(CREATE_CERTIFICATE_QUEUE);
            ps.setString(1, certificate.getName());
            ps.setString(2, certificate.getDescription());
            ps.setInt(3, certificate.getPrice());
            ps.setInt(4, certificate.getDuration());
            ps.setString(5, certificate.getCreateDate().toString());
            ps.setString(6, certificate.getLastUpdateDate().toString());

            return ps;
        }, keyHolder);

        return keyHolder.getKeyAs(Integer.TYPE);
    }

    private boolean createCertificateTagsReferences(Certificate certificate) {
        int affectedRows = 0;
        for (Tag tag : certificate.getTags()) {
            affectedRows += jdbcTemplate.update(CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE, certificate.getId(), tag.getId());
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
        return jdbcTemplate.update(UPDATE_CERTIFICATE_BY_ID_QUEUE, certificate.getName(), certificate.getDescription(),
                certificate.getPrice(), certificate.getDuration(), certificate.getLastUpdateDate(),
                certificate.getId()) == 1;
    }

    private void updateCertificateTagsInDB(Certificate certificate) {
        jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, certificate.getId());
        for (Tag tag : certificate.getTags()) {
            jdbcTemplate.update(CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE, certificate.getId(), tag.getId());
        }
    }

    @Transactional
    @Override
    public boolean deleteCertificate(int id) throws DaoException {
        try {
            return jdbcTemplate.update(DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE, id) == 1 &&
                    jdbcTemplate.update(DELETE_CERTIFICATE_BY_ID_QUEUE, id) >= 0;
        } catch (DataAccessException exception) {
            logger.error("couldn't delete certificate");
            throw new DaoException("can't delete certificate");
        }
    }
}
