package test.com.epam.esm.controller;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.dao.CertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.util.parser.DateTimeParser;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateControllerTest {
    private EmbeddedDatabase embeddedDatabase;
    private CertificateDao certificateDao;

    CertificateControllerTest() {
    }

    @BeforeEach
    void setUp() {
        this.embeddedDatabase = (new EmbeddedDatabaseBuilder()).addDefaultScripts().setType(EmbeddedDatabaseType.H2).build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(this.embeddedDatabase);
        TagDao tagDao = new TagDaoImpl(jdbcTemplate);
        this.certificateDao = new CertificateDaoImpl(jdbcTemplate, tagDao, new DateTimeParser());
    }

    @AfterEach
    void tearDown() {
        this.embeddedDatabase.shutdown();
    }

    @ParameterizedTest
    @MethodSource("databaseCertificates")
    void index(List<Certificate> expectedCertificates) {
        CertificateController controller = new CertificateController(new CertificateServiceImpl(certificateDao));
        try {
            List<Certificate> actualCertificates = controller.index();
            assertEquals(expectedCertificates, actualCertificates);
        } catch (ControllerException e) {
            fail(e);
        }
    }

    static Stream<Arguments> databaseCertificates() {
        Tag chillTag = new Tag(1, "chill");
        Tag sportTag = new Tag(2, "sport");
        Tag relaxTag = new Tag(3, "relax");
        Certificate spaCertificate = new Certificate(1, "spa", "using this certificate u can go to spa", 15, 15,
                LocalDateTime.of(2020, 12, 12, 12, 0,0,0),
                LocalDateTime.of(2020, 12, 12, 12, 0,0,0),
                Arrays.asList(chillTag, relaxTag));
        Certificate gymCertificate = new Certificate(2, "gym", "15 days of free gym", 20, 15,
                LocalDateTime.of(2020, 12, 15, 13, 0,0),
                LocalDateTime.of(2020, 12, 15, 13, 0,0),
                Collections.singletonList(sportTag));
        return Stream.of(
                Arguments.of(Arrays.asList(spaCertificate, gymCertificate))
        );
    }

    @ParameterizedTest
    @MethodSource("firstIdCertificate")
    void getCertificate(Certificate expectedCertificate, int id) {
        CertificateController controller = new CertificateController(new CertificateServiceImpl(certificateDao));
        try {
            Certificate actualCertificate = controller.getCertificate(id);
            assertEquals(expectedCertificate, actualCertificate);
        } catch (ControllerException e) {
            fail(e);
        }
    }

    static Stream<Arguments> firstIdCertificate() {
        Tag chillTag = new Tag(1, "chill");
        Tag relaxTag = new Tag(3, "relax");
        Certificate spaCertificate = new Certificate(1, "spa", "using this certificate u can go to spa", 15, 15,
                LocalDateTime.of(2020, 12, 12, 12, 0,0,0),
                LocalDateTime.of(2020, 12, 12, 12, 0,0,0),
                Arrays.asList(chillTag, relaxTag));

        return Stream.of(
                Arguments.of(spaCertificate, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("certificateToCreate")
    void createCertificate(String certificateName, String certificateDescription, int certificatePrice,
                           int certificateDuration, List<Tag> certificateTags, JsonAnswer expectedAnswer) {
        CertificateController controller = new CertificateController(new CertificateServiceImpl(certificateDao));
        JsonAnswer actualAnswer = controller.createCertificate(certificateName, certificateDescription,certificatePrice,
                certificateDuration, certificateTags);
        assertEquals(expectedAnswer, actualAnswer);
    }

    static Stream<Arguments> certificateToCreate() {
        Tag funTag = new Tag(4, "fun");

        JsonAnswer answer = new JsonAnswer(HttpStatus.OK, "created new certificate");
        return Stream.of(
                Arguments.of("circus", "using this certificate u can visit circus", 0, 365,
                        Collections.singletonList(funTag), answer)
        );
    }

    @ParameterizedTest
    @MethodSource("certificateToUpdate")
    void updateCertificate(int id, String certificateName, String certificateDescription, int certificatePrice,
                           int certificateDuration, List<Tag> certificateTags, JsonAnswer expectedAnswer) {
        CertificateController controller = new CertificateController(new CertificateServiceImpl(certificateDao));
        JsonAnswer actualAnswer = controller.updateCertificate(id, certificateName, certificateDescription,certificatePrice,
                certificateDuration, certificateTags);
        assertEquals(expectedAnswer, actualAnswer);
    }

    static Stream<Arguments> certificateToUpdate() {
        Tag funTag = new Tag(4, "fun");
        Tag trashTag = new Tag(5, "trash");
        Certificate certificate = new Certificate("circus in our life", "using this certificate u will see that our life is just a circus", 0, 365,
                LocalDateTime.now(), LocalDateTime.now(), Collections.singletonList(funTag));
        JsonAnswer answer = new JsonAnswer(HttpStatus.OK, "updated certificate");

        return Stream.of(
                Arguments.of("circus in our life", "using this certificate u will see that our life is just a circus",
                        0, 365, Arrays.asList(funTag, trashTag), answer)
        );
    }

    @ParameterizedTest
    @MethodSource("certificatesId")
    void deleteCertificate(int id, JsonAnswer expectedAnswer) {
        CertificateController controller = new CertificateController(new CertificateServiceImpl(certificateDao));
        JsonAnswer actualAnswer = controller.deleteCertificate(id);
        assertEquals(expectedAnswer, actualAnswer);
    }

    static Stream<Arguments> certificatesId() {
        return Stream.of(
                Arguments.of(1, new JsonAnswer(HttpStatus.OK, "deleted certificate"))
        );
    }
}