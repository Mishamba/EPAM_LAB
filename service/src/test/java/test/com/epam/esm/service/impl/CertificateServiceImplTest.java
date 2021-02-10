package test.com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.util.comparator.certificate.CertificateComparatorFactory;
import com.epam.esm.model.util.entity.PaginationData;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateServiceImplTest {

    @ParameterizedTest
    @MethodSource("provideCertificatesWithPage")
    void findAllCertificates(List<Certificate> expectedCertificates, int pageNumber) {
        CertificateDao dao = Mockito.mock(CertificateDao.class);

        Mockito.when(dao.findAllCertificates(pageNumber)).thenReturn(expectedCertificates);

        CertificateService service = new CertificateServiceImpl(dao, new CertificateComparatorFactory());

        List<Certificate> actualCertificates = null;
        try {
            actualCertificates = service.findAllCertificates(new PaginationData(null, null, 1));
            assertEquals(expectedCertificates, actualCertificates);
        } catch (ServiceException exception) {
            fail("fail with exception");
        }
    }

    private static Stream<Arguments> provideCertificatesWithPage() {
        Tag sportTag = new Tag(1, "sport");
        Tag adrenalineTag = new Tag(2, "adrenaline");
        Tag chillTag = new Tag(3, "chill");
        Tag planeTag = new Tag(4, "plane");
        Tag moonTag = new Tag(5, "moon");
        Certificate simpleGymCertificate = new Certificate(1, "gym", "15 days gym certificate", 50, 20,
                LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(adrenalineTag, sportTag));
        Certificate gymAtPlane = new Certificate(2, "gym at plane", "train in plane", 100, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, adrenalineTag, planeTag));
        Certificate midnightGym = new Certificate(3, "gym at night", "train at night", 60, 30, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, moonTag));
        Certificate oneHourSleepCertificate = new Certificate(4, "sleep certificate",
                "using this certificate u can sleep one more hour a day", 10000, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(chillTag, moonTag));
        List<Certificate> certificates = Arrays.asList(oneHourSleepCertificate, simpleGymCertificate, midnightGym,
                gymAtPlane);
        return Stream.of(
                Arguments.of(certificates, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificateWithId")
    void findCertificateById(Certificate expectedCertificate, int id) {
        CertificateDao dao = Mockito.mock(CertificateDao.class);

        Mockito.when(dao.findCertificateById(id)).thenReturn(expectedCertificate);

        CertificateService service = new CertificateServiceImpl(dao, new CertificateComparatorFactory());

        Certificate actualCertificate = service.findCertificateById(id);
        assertEquals(expectedCertificate, actualCertificate);
    }

    private static Stream<Arguments> provideCertificateWithId() {
        Tag sportTag = new Tag(1, "sport");
        Tag adrenalineTag = new Tag(2, "adrenaline");
        Certificate simpleGymCertificate = new Certificate(1, "gym", "15 days gym certificate", 50, 20,
                LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(adrenalineTag, sportTag));
        return Stream.of(
                Arguments.of(simpleGymCertificate, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificatesWithTagName")
    void findCertificatesByTag(List<Certificate> expectedCertificates, String tagName, int pageNumber) {
        CertificateDao dao = Mockito.mock(CertificateDao.class);

        Mockito.when(dao.findCertificatesByTag(tagName, pageNumber)).thenReturn(expectedCertificates);

        CertificateService service = new CertificateServiceImpl(dao, new CertificateComparatorFactory());

        try {
            List<Certificate> actualCertificate = service.findCertificatesByTag(tagName,
                    new PaginationData(null, null, pageNumber));
            assertEquals(expectedCertificates, actualCertificate);
        } catch (ServiceException exception) {
            fail("fail with exception");
        }
    }

    private static Stream<Arguments> provideCertificatesWithTagName() {
        Tag sportTag = new Tag(1, "sport");
        Tag adrenalineTag = new Tag(2, "adrenaline");
        Tag chillTag = new Tag(3, "chill");
        Tag planeTag = new Tag(4, "plane");
        Tag moonTag = new Tag(5, "moon");
        Certificate simpleGymCertificate = new Certificate(1, "gym", "15 days gym certificate", 50, 20,
                LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(adrenalineTag, sportTag));
        Certificate gymAtPlane = new Certificate(2, "gym at plane", "train in plane", 100, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, adrenalineTag, planeTag));
        Certificate midnightGym = new Certificate(3, "gym at night", "train at night", 60, 30, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, moonTag, adrenalineTag));
        Certificate oneHourSleepCertificate = new Certificate(4, "sleep certificate",
                "using this certificate u can sleep one more hour a day", 10000, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(chillTag, moonTag));
        List<Certificate> sportCertificates = Arrays.asList(simpleGymCertificate, gymAtPlane, midnightGym);
        List<Certificate> chillCertificates = Collections.singletonList(oneHourSleepCertificate);
        List<Certificate> moonCertificates = Arrays.asList(oneHourSleepCertificate, midnightGym);
        return Stream.of(
                Arguments.of(sportCertificates, sportTag.getName(), 1),
                Arguments.of(chillCertificates, chillTag.getName(), 1),
                Arguments.of(moonCertificates, moonTag.getName(), 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificatesWithNameAndDescription")
    void findCertificatesByNameAndDescription(List<Certificate> expectedCertificates, String name, String description,
                                              int pageNumber) {
        CertificateDao dao = Mockito.mock(CertificateDao.class);

        Mockito.when(dao.findCertificatesByNameAndDescription(name, description, pageNumber)).
                thenReturn(expectedCertificates);

        CertificateService service = new CertificateServiceImpl(dao, new CertificateComparatorFactory());

        try {
            List<Certificate> actualCertificates = service.findCertificatesByNameAndDescription(name, description,
                    new PaginationData(null, null, pageNumber));
            assertEquals(expectedCertificates, actualCertificates);
        } catch (ServiceException exception) {
            fail("fail with exception");
        }
    }

    private static Stream<Arguments> provideCertificatesWithNameAndDescription() {
        Tag sportTag = new Tag(1, "sport");
        Tag adrenalineTag = new Tag(2, "adrenaline");
        Tag planeTag = new Tag(4, "plane");
        Tag moonTag = new Tag(5, "moon");
        Certificate simpleGymCertificate = new Certificate(1, "gym", "15 days gym train certificate", 50, 20,
                LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(adrenalineTag, sportTag));
        Certificate gymAtPlane = new Certificate(2, "gym at plane", "train in plane", 100, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, adrenalineTag, planeTag));
        Certificate midnightGym = new Certificate(3, "gym at night", "train at night", 60, 30, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, moonTag, adrenalineTag));
        List<Certificate> gymTrainCertificates = Arrays.asList(simpleGymCertificate, gymAtPlane, midnightGym);
        return Stream.of(
                Arguments.of(gymTrainCertificates, "gym", "train", 1)
        );
    }
}