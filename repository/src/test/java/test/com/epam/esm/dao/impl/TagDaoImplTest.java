package test.com.epam.esm.dao.impl;

import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class TagDaoImplTest {

    @ParameterizedTest
    @MethodSource("provideTags")
    void findAllTags(List<Tag> tagList) {
    }

    static Stream<Arguments> provideTags() {
        Tag tag1 = new Tag(1, "some name");
        Tag tag2 = new Tag(2, "another name");
        List<Tag> tagList = Arrays.asList(tag1, tag2);
        return Stream.of(
                Arguments.of(tagList)
        );
    }

    @Test
    void findTagById() {
    }

    @Test
    void createTag() {
    }

    @Test
    void deleteTag() {
    }
}