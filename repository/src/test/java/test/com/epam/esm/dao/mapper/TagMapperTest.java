package test.com.epam.esm.dao.mapper;

import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TagMapperTest {

    static Stream<Arguments> resultSetTag() {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(resultSet.getInt("id")).thenReturn(1);
            Mockito.when(resultSet.getString("_name")).thenReturn("smth");
        } catch (SQLException e) {
            fail();
        }

        Tag tag = new Tag(1, "smth");

        return Stream.of(
                Arguments.of(resultSet, tag)
        );
    }

    @ParameterizedTest
    @MethodSource("resultSetTag")
    void mapRow(ResultSet resultSet, Tag expectedTag) {
        TagMapper mapper = new TagMapper();

        try {
            Tag actualTag = mapper.mapRow(resultSet, 1);
            assertEquals(expectedTag, actualTag);
        } catch (SQLException e) {
            fail();
        }
    }
}