package test.com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.CertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.dao.impl.TagDaoImpl;
import com.epam.esm.model.dao.queue.QueryRepository;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
import com.epam.esm.util.parser.DateTimeParser;
import org.junit.jupiter.api.Test;
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
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class CertificateDaoImplTest {

    private static final int nanosecondsMultiplier = (int) Math.pow(10, 6);

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
    @MethodSource("providerCertificateList")
    void findAllCertificates(List<Certificate> certificateList) {
        JdbcTemplate jdbcTemplateMock = Mockito.mock(JdbcTemplate.class);
        DateTimeParser dateTimeParser = new DateTimeParser();
        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        Mockito.when(jdbcTemplateMock.query(Mockito.same(QueryRepository.ALL_CERTIFICATES_QUEUE), (RowMapper<Certificate>) Mockito.any())).
                thenReturn(certificateList);

        CertificateDao certificateDao = new CertificateDaoImpl(jdbcTemplateMock, tagDao, dateTimeParser);

        try {
            List<Certificate> actualCertificateList = certificateDao.findAllCertificates();
            assertEquals(certificateList, actualCertificateList);
        } catch (DaoException e) {
            fail("got dao exception", e);
        }

    }

    // TODO: 1/20/21 finish tests

    @Test
    void findCertificateById() {
    }

    @Test
    void createCertificate() {
    }

    @Test
    void updateCertificate() {
    }

    @Test
    void deleteCertificate() {
    }
}