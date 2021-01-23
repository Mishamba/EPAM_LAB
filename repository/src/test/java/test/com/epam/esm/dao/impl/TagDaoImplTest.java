package test.com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.exception.DaoException;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.dao.queue.TagQueryRepository;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {

    @ParameterizedTest
    @MethodSource("provideTags")
    void findAllTags(List<Tag> expectedTagList) {
        JdbcTemplate jdbcTemplateMock = (JdbcTemplate) Mockito.when(JdbcTemplate.class);

        Mockito.when(jdbcTemplateMock.query(Mockito.eq(TagQueryRepository.ALL_TAGS_QUEUE),
                (RowMapper<Object>) Mockito.any())).thenReturn(Collections.singletonList(expectedTagList));

        TagDao tagDao = new TagDaoImpl(jdbcTemplateMock);

        try {
            List<Tag> actualTagList = tagDao.findAllTags();
            assertEquals(expectedTagList, actualTagList);
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    static Stream<Arguments> provideTags() {
        Tag tag1 = new Tag(1, "some name");
        Tag tag2 = new Tag(2, "another name");
        List<Tag> tagList = Arrays.asList(tag1, tag2);
        return Stream.of(
                Arguments.of(tagList)
        );
    }

    @ParameterizedTest
    @MethodSource("tagAndId")
    void findTagById(Tag tag, int id) {

    }

    static Stream<Arguments> tagAndId() {
        return Stream.of(
                Arguments.of(new Tag(1, "some name"), 1)
        );
    }

    @Test
    void createTag() {
    }

    @Test
    void deleteTag() {
    }
}