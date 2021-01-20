package test.com.epam.esm.model.dao.mapper;

import com.epam.esm.model.dao.mapper.IntegerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class IntegerMapperTest {

    static Stream<Arguments> resultSetInteger() {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(resultSet.getInt(1)).thenReturn(5);
        } catch (SQLException e) {
            fail(e);
        }

        return Stream.of(
                Arguments.of(resultSet, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("resultSetInteger")
    void mapRow(ResultSet resultSet, Integer expected) {
        IntegerMapper mapper = new IntegerMapper();
        try {
            Integer actual = mapper.mapRow(resultSet, 1);
            assertEquals(expected, actual);
        } catch (SQLException e) {
            fail(e);
        }
    }
}