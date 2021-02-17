package test.com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import test.com.epam.esm.config.JPAConfig;

import java.util.List;

@SpringBootTest(classes = TagDaoImpl.class)
@ContextConfiguration(classes = JPAConfig.class)
class TagDaoImplTest {

    @Autowired
    private TagDao tagDao;

    @Test
    @Transactional
    void findAllTags() {
        List<Tag> tags = tagDao.findAllTags(1);

        tags.forEach(System.out::println);
        System.out.println("message");
    }

    @Test
    @Transactional
    void findTagById() {
        System.out.println(tagDao.findTagById(5));
    }

    @Test
    @Transactional
    void findTagByName() {
        System.out.println(tagDao.findTagByName("smth"));
    }

    @Test
    @Transactional
    void createTag() {
        Tag tag = new Tag("some name");

        tagDao.createTag(tag);
    }

    @BeforeEach
    void createTagBefore() {
        Tag tag1 = new Tag("some name");
        Tag tag2 = new Tag("smth");

        tagDao.createTag(tag1);
        tagDao.createTag(tag2);
    }

    @Test
    @Transactional
    void deleteTag() {
        tagDao.deleteTag(5);
        List<Tag> tagList = tagDao.findAllTags(1);

        tagList.forEach(System.out::println);
    }
}