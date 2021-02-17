package test.com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.com.epam.esm.config.JPAConfig;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CertificateDaoImpl.class)
@ContextConfiguration(classes = JPAConfig.class)
class CertificateDaoImplTest {

    @Autowired
    private static CertificateDao dao;

    @ParameterizedTest
    @MethodSource("provideCertificatesToFind")
    @Order(value = 2)
    void findAllCertificates(List<Certificate> expectedCertificates, int pageNumber) {
        List<Certificate> actualCertificates = dao.findAllCertificates(pageNumber);

        assertEquals(actualCertificates, expectedCertificates);
    }

    private static Stream<Arguments> provideCertificatesToFind() {
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
        List<Certificate> certificates = Arrays.asList(simpleGymCertificate, midnightGym, gymAtPlane,
                oneHourSleepCertificate);
        return Stream.of(
                Arguments.of(certificates, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificateWithId")
    @Order(value = 3)
    void findCertificateById(Certificate expectedCertificate, int certificateId) {
        Certificate actualCertificate = dao.findCertificateById(certificateId);

        assertEquals(expectedCertificate, actualCertificate);
    }

    private static Stream<Arguments> provideCertificateWithId() {
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
        return Stream.of(
                Arguments.of(simpleGymCertificate, simpleGymCertificate.getId()),
                Arguments.of(gymAtPlane, gymAtPlane.getId()),
                Arguments.of(midnightGym, midnightGym.getId()),
                Arguments.of(oneHourSleepCertificate, oneHourSleepCertificate.getId())
        );
    }

    @ParameterizedTest
    @MethodSource("provideCertificatesWithTagName")
    @Order(value = 4)
    void findCertificatesByTag(List<Certificate> expectedCertificate, String tagName, int pageNumber) {
        List<Certificate> actualCertificate = dao.findCertificatesByTag(tagName, pageNumber);

        assertEquals(expectedCertificate, actualCertificate);
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
    @Order(value = 5)
    void findCertificatesByNameAndDescription(List<Certificate> expectedCertificates,
                                              String certificateName, String description, int pageNumber) {
        List<Certificate> actualCertificates = dao.
                findCertificatesByNameAndDescription(certificateName, description, pageNumber);

        assertEquals(expectedCertificates, actualCertificates);
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

    @ParameterizedTest
    @MethodSource(value = "provideCertificates")
    @Order(value = 1)
    @Transactional
    void createCertificate(Certificate certificate) {
        dao.createCertificate(certificate);
    }

    private static Stream<Arguments> provideCertificates() {
        Tag sportTag = new Tag("sport");
        Tag adrenalineTag = new Tag("adrenaline");
        Tag chillTag = new Tag("chill");
        Tag planeTag = new Tag("plane");
        Tag moonTag = new Tag("moon");
        Certificate simpleGymCertificate = new Certificate("gym", "15 days gym certificate", 50, 20,
                LocalDateTime.now(), LocalDateTime.now(), Arrays.asList(adrenalineTag, sportTag));
        Certificate gymAtPlane = new Certificate("gym at plane", "train in plane", 100, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, adrenalineTag, planeTag));
        Certificate midnightGym = new Certificate("gym at night", "train at night", 60, 30, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(sportTag, moonTag));
        Certificate oneHourSleepCertificate = new Certificate("sleep certificate",
                "using this certificate u can sleep one more hour a day", 10000, 20, LocalDateTime.now(),
                LocalDateTime.now(), Arrays.asList(chillTag, moonTag));
        return Stream.of(
                Arguments.of(simpleGymCertificate),
                Arguments.of(gymAtPlane),
                Arguments.of(midnightGym),
                Arguments.of(oneHourSleepCertificate)
        );
    }

    @ParameterizedTest
    @MethodSource("provideIds")
    @Order(value = 6)
    @Transactional
    void deleteCertificate(int certificateId) {
        dao.deleteCertificate(certificateId);
    }

    private static Stream<Arguments> provideIds() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(3),
                Arguments.of(4)
        );
    }
}