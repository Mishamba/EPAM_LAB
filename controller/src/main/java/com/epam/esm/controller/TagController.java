package com.epam.esm.controller;

import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.controller.json.entity.JsonError;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @version 1.0
 * @author mishamba
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final Logger logger = Logger.getLogger(TagController.class);

    /**
     * @param tagService Service layer component.
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * Returns all Tags stored in database.
     *
     * @return All stored Tags.
     * @throws ControllerException
     */
    @GetMapping("/get/all")
    public List<Tag> index(@RequestParam(value = "page_number", defaultValue = "1") int pageNumber) throws ControllerException {
        try {
            return tagService.findAllTags(new PaginationData(null, null, pageNumber));
        } catch (ServiceException exception) {
            logger.error("unsuccessful tags selection. exit with exception");
            throw new ControllerException("can't get tags", exception);
        }
    }

    /**
     * Returns Tag with given id.
     *
     * @param id Tag id.
     * @return Tag with given id.
     * @throws ControllerException
     */
    @GetMapping("/get/{id}")
    public Tag getTagById(@PathVariable("id") int id) throws ControllerException {
        Tag tag = tagService.findTagById(id);
        ifNullThrowException(tag);
        return tag;
    }

    private void ifNullThrowException(Object o) throws ControllerException {
        if (o == null) {
            throw new ControllerException("no tag found.");
        }
    }

    /**
     * Creates and stores Tag in database.
     *
     * @param name New tag name.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PostMapping("/create")
    public JsonAnswer createTag(@RequestParam("name") String name) {
        tagService.createTag(new Tag(name));
        return new JsonAnswer(HttpStatus.OK, "successfully created new tag");
    }

    /**
     * Deletes tag from database using tag id.
     *
     * @param id Delete tag id.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @DeleteMapping("/delete/{id}")
    public JsonAnswer deleteTag(@PathVariable("id") int id) {
        tagService.deleteTag(id);
        return new JsonAnswer(HttpStatus.OK, "successfully deleted tag");
    }
}
