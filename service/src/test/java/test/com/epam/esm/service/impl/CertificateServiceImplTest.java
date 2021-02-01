package test.com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateServiceImplTest {
    private final static int nanosecondsMultiplier = (int) Math.pow(10, 6);


    @ParameterizedTest
    @MethodSource("providerCertificateList")
    void findAllCertificates(List<Certificate> expectedCertificates) {
        CertificateDao certificateDao = Mockito.mock(CertificateDao.class);

        try {
            Mockito.when(certificateDao.findAllCertificates()).thenReturn(expectedCertificates);

            CertificateService certificateService = new CertificateServiceImpl(certificateDao);

            List<Certificate> actualCertificates = certificateService.findAllCertificates();
            assertEquals(expectedCertificates, actualCertificates);
        } catch (ServiceException | DaoException exception) {
            fail(exception);
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
    @MethodSource("provideCertficateAndId")
    void findCertificateById(Certificate expectedCertificate, int id) {
        CertificateDao certificateDao = Mockito.mock(CertificateDao.class);

        try {
            Mockito.when(certificateDao.findCertificateById(id)).thenReturn(expectedCertificate);

            CertificateService certificateService = new CertificateServiceImpl(certificateDao);

            Certificate actualCertificate = certificateService.findCertificateById(id);
            assertEquals(expectedCertificate, actualCertificate);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    static Stream<Arguments> provideCertficateAndId() {
        Tag tag1 = new Tag(1, "certificate12tag");
        Tag tag2 = new Tag(2, "certificate1tag");
        List<Tag> tagList1 = Arrays.asList(tag1, tag2);
        Certificate certificate = new Certificate(1, "certificate1", "some description", 15, 53,
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                        tagList1);
        return Stream.of(
                Arguments.of(certificate, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificateAndBoolean")
    void createCertificate(Certificate certificate, boolean expected) {
        CertificateDao certificateDao = Mockito.mock(CertificateDao.class);

        try {
            Mockito.when(certificateDao.createCertificate(certificate)).thenReturn(true);

            CertificateService certificateService = new CertificateServiceImpl(certificateDao);
            boolean actual = certificateService.createCertificate(certificate);
            assertEquals(expected, actual);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("provideCertificateAndBoolean")
    void updateCertificate(Certificate certificate, boolean expected) {
        CertificateDao certificateDao = Mockito.mock(CertificateDao.class);

        try {
            Mockito.when(certificateDao.updateCertificate(certificate)).thenReturn(true);

            CertificateService certificateService = new CertificateServiceImpl(certificateDao);
            boolean actual = certificateService.updateCertificate(certificate);
            assertEquals(expected, actual);
        } catch (DaoException | ServiceException e) {
            fail();
        }
    }

    static Stream<Arguments> provideCertificateAndBoolean() {
        Tag tag1 = new Tag(1, "certificate12tag");
        Tag tag2 = new Tag(2, "certificate1tag");
        List<Tag> tagList1 = Arrays.asList(tag1, tag2);
        Certificate certificate = new Certificate(1, "certificate1", "some description", 15, 53,
                LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                LocalDateTime.of(2021, Month.DECEMBER, 12, 15, 6, 22),
                tagList1);

        return Stream.of(
                Arguments.of(certificate, true)
        );
    }

    @Test
    void deleteCertificate() {
        int id = 5;

        CertificateDao certificateDao = Mockito.mock(CertificateDao.class);

        try {
            Mockito.when(certificateDao.deleteCertificate(id)).thenReturn(true);

            CertificateService certificateService = new CertificateServiceImpl(certificateDao);
            boolean actual = certificateService.deleteCertificate(id);
            assertTrue(actual);
        } catch (DaoException | ServiceException e) {
            fail(e);
        }
    }
}