package test.com.epam.esm.model.dao.mapper;

import com.epam.esm.model.dao.mapper.CertificateWithoutTagsMapper;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.parser.DateTimeParser;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CertificateWithoutTagsMapperTest {

    private static final int nanosecondsMultiplier = (int) Math.pow(10, 6);

    @ParameterizedTest
    @MethodSource("provideCertificateResultSet")
    void mapRow(ResultSet resultSet, Certificate expectedCertificate) {
        DateTimeParser parser = new DateTimeParser();
        CertificateWithoutTagsMapper mapper = new CertificateWithoutTagsMapper(parser);
        try {
            Certificate actualCertificate = mapper.mapRow(resultSet, 1);
            assertEquals(expectedCertificate, actualCertificate);
        } catch (SQLException e) {
            fail(e);
        }
    }

    static Stream<Arguments> provideCertificateResultSet() {
        ResultSet resultSet = Mockito.mock(ResultSet.class);

        try {
            Mockito.when(resultSet.getInt("id")).thenReturn(1);
            Mockito.when(resultSet.getString("_name")).thenReturn("some name");
            Mockito.when(resultSet.getString("description")).thenReturn("some description");
            Mockito.when(resultSet.getInt("price")).thenReturn(100);
            Mockito.when(resultSet.getInt("duration")).thenReturn(30);
            Mockito.when(resultSet.getString("create_date")).thenReturn("2000-11-10T12:12:12.122");
            Mockito.when(resultSet.getString("last_update_date")).thenReturn("2000-11-10T12:12:12.122");
        } catch (SQLException e) {
            fail(e);
        }

        Certificate certificate = new Certificate(1, "some name", "some description", 100, 30,
                LocalDateTime.of(2000, Month.NOVEMBER, 10, 12, 12, 12, 122 * nanosecondsMultiplier),
                LocalDateTime.of(2000, Month.NOVEMBER, 10, 12, 12, 12, 122 * nanosecondsMultiplier),
                null);

        return Stream.of(
                Arguments.of(resultSet, certificate)
        );
    }
}