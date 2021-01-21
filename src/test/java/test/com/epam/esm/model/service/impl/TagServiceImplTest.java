package test.com.epam.esm.model.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.DaoException;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.TagService;
import com.epam.esm.model.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {

    @ParameterizedTest
    @MethodSource("provideTags")
    void findAllTags(List<Tag> expectedTagList) {
        TagDao tagDao = Mockito.mock(TagDao.class);

        try {
            Mockito.when(tagDao.findAllTags()).thenReturn(expectedTagList);
            TagService tagService = new TagServiceImpl(tagDao);
            List<Tag> actualTagList = tagService.findAllTags();
            assertEquals(actualTagList, expectedTagList);
        } catch (DaoException | ServiceException e) {
            fail(e);
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
    @MethodSource("provideTag")
    void findTagById(Tag tag, int id) {
        TagDao tagDao = Mockito.mock(TagDao.class);

        try {
            Mockito.when(tagDao.findTagById(id)).thenReturn(tag);

            TagService tagService = new TagServiceImpl(tagDao);
            Tag actualTag = tagService.findTagById(id);

            assertEquals(tag, actualTag);
        } catch (DaoException | ServiceException e) {
            fail(e);
        }
    }

    static Stream<Arguments> provideTag() {
        Tag tag1 = new Tag(1, "some name");
        return Stream.of(
                Arguments.of(tag1, 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTagAndBoolean")
    void createTag(Tag tagToCreate, boolean expected) {
        TagDao tagDao = Mockito.mock(TagDao.class);

        try {
            Mockito.when(tagDao.createTag(tagToCreate)).thenReturn(expected);

            TagService tagService = new TagServiceImpl(tagDao);
            boolean actual = tagService.createTag(tagToCreate);
            assertEquals(expected, actual);
        } catch (DaoException | ServiceException exception) {
            fail(exception);
        }
    }

    static Stream<Arguments> provideTagAndBoolean() {
        Tag tag1 = new Tag(1, "some name");
        return Stream.of(
                Arguments.of(tag1, true)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTagIdAndBoolean")
    void deleteTag(int id, boolean expected) {
        TagDao tagDao = Mockito.mock(TagDao.class);

        try {
            Mockito.when(tagDao.deleteTag(id)).thenReturn(expected);

            TagService tagService = new TagServiceImpl(tagDao);
            boolean actual = tagService.deleteTag(id);
            assertEquals(expected, actual);
        } catch (DaoException | ServiceException e) {
            fail(e);
        }
    }

    static Stream<Arguments> provideTagIdAndBoolean() {
        return Stream.of(
                Arguments.of(5, true)
        );
    }
}