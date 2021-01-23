package test.com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.queue.CertificateQueryRepository;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.dao.util.parser.DateTimeParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateDaoImplTest {

    private static final int nanosecondsMultiplier = (int) Math.pow(10, 6);

    @ParameterizedTest
    @MethodSource("providerCertificateList")
    void findAllCertificates(List<Certificate> certificateList) {
        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        DateTimeParser dateTimeParser = new DateTimeParser();
        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        Mockito.when(jdbcTemplateMock.query(Mockito.same(CertificateQueryRepository.ALL_CERTIFICATES_QUEUE), (RowMapper<Certificate>) Mockito.any())).
                thenReturn(certificateList);

        CertificateDao certificateDao = new CertificateDaoImpl(jdbcTemplateMock, tagDao, dateTimeParser);

        try {
            List<Certificate> actualCertificateList = certificateDao.findAllCertificates();
            assertEquals(certificateList, actualCertificateList);
        } catch (DaoException e) {
            fail(e);
        }
    }

    static Stream<Arguments> providerCertificateList() {
        Tag tag1 = new Tag(1, "certificate12tag");
        Tag tag2 = new Tag(2, "certificate1tag");
        List<Tag> tagList1 = Arrays.asList(tag1, tag2);
        List<Tag> tagList2 = Collections.singletonList(tag1);
        List<Certificate> certificateList = Arrays.asList(
                new Certificate(1, "certificate1", "some description", 15, 53,
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        tagList1),
                new Certificate(2, "certificate2", "custom description", 20, 20,
                        LocalDateTime.of(2020, Month.AUGUST, 15, 8, 30, 36, 20 * nanosecondsMultiplier),
                        LocalDateTime.of(2020, Month.AUGUST, 18, 9, 31, 57, 21 * nanosecondsMultiplier),
                        tagList2));

        return Stream.of(
                Arguments.of(certificateList)
        );
    }

    @ParameterizedTest
    @MethodSource("certificateWithId")
    void findCertificateById(Certificate expectedCertificate, int id) {
        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        DateTimeParser dateTimeParser = new DateTimeParser();
        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        Mockito.when(jdbcTemplateMock.query(Mockito.same(CertificateQueryRepository.CERTIFICATE_BY_ID_QUEUE),
                (RowMapper<Certificate>) Mockito.any(), Mockito.eq(id)).stream().findAny()).
                thenReturn(Optional.of(expectedCertificate));

        CertificateDao certificateDao = new CertificateDaoImpl(jdbcTemplateMock, tagDao, dateTimeParser);

        try {
            Certificate actualCertificate = certificateDao.findCertificateById(id);
            assertEquals(expectedCertificate, actualCertificate);
        } catch (DaoException e) {
            fail(e);
        }
    }

    static Stream<Arguments> certificateWithId() {
        Tag tag1 = new Tag(1, "certificate12tag");
        Tag tag2 = new Tag(2, "certificate1tag");
        Certificate certificate = new Certificate(1, "certificate1", "some description", 15, 53,
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        Arrays.asList(tag1, tag2));
        return Stream.of(
                Arguments.of(certificate, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("certificateToUpdate")
    void updateCertificate(Certificate certificate, boolean expected) {
        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        DateTimeParser dateTimeParser = new DateTimeParser();
        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        // This methods return value never used in update method.
        Mockito.when(jdbcTemplateMock.update(CertificateQueryRepository.UPDATE_CERTIFICATE_BY_ID_QUEUE,
                certificate.getName(), certificate.getDescription(), certificate.getPrice(), certificate.getDuration(),
                certificate.getLastUpdateDate(), certificate.getId())).thenReturn(1);
        Mockito.when(jdbcTemplateMock.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE,
                certificate.getId())).thenReturn(1);
        Mockito.when(jdbcTemplateMock.update(
                Mockito.eq(CertificateQueryRepository.CREATE_CERTIFICATE_TAGS_REFERENCES_QUEUE),
                Mockito.eq(certificate.getId()), Mockito.any())).thenReturn(1);

        CertificateDao certificateDao = new CertificateDaoImpl(jdbcTemplateMock, tagDao, dateTimeParser);
        try {
            boolean actual = certificateDao.updateCertificate(certificate);
            assertEquals(expected, actual);
        } catch (DaoException e) {
            fail(e);
        }
    }

    static Stream<Arguments> certificateToUpdate() {
        Tag tag1 = new Tag(1, "certificate12tag");
        Tag tag2 = new Tag(2, "certificate1tag");
        Certificate certificate = new Certificate(1, "certificate1", "some description", 15, 53,
                LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                Arrays.asList(tag1, tag2));
        return Stream.of(
                Arguments.of(certificate, true)
        );
    }

    @ParameterizedTest
    @MethodSource("certificateId")
    void deleteCertificate(int id, boolean expected) {
        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        DateTimeParser dateTimeParser = new DateTimeParser();
        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        Mockito.when(jdbcTemplateMock.update(CertificateQueryRepository.DELETE_CERTIFICATE_TAGS_BY_ID_REFERENCES_QUEUE,
                id)).thenReturn(1);
        Mockito.when(jdbcTemplateMock.update(CertificateQueryRepository.DELETE_CERTIFICATE_BY_ID_QUEUE, id)).
                thenReturn(1);

        CertificateDao certificateDao = new CertificateDaoImpl(jdbcTemplateMock, tagDao, dateTimeParser);
        try {
            boolean actual = certificateDao.deleteCertificate(id);
            assertEquals(expected, actual);
        } catch (DaoException e) {
            fail(e);
        }
    }

    static Stream<Arguments> certificateId() {
        return Stream.of(
                Arguments.of(1, true)
        );
    }
}