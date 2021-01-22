package com.epam.esm.controller;

import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.controller.json.entity.JsonError;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This is controller layer class. Class used everytime when url pattern starts with "/tag/*" part
 * Each method call depends on used http method and url path.
 *
 * @version 1.0
 * @author mishamba
 */
@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final Logger logger = Logger.getLogger(TagController.class);

    /**
     * Constructor. Gets parameters from Spring using @Autowired.
     *
     * @param tagService Service layer component.
     */
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    /**
     * This method returns all Tags stored in database.
     *
     * @return All stored Tags.
     * @throws ControllerException
     */
    @GetMapping
    public List<Tag> index() throws ControllerException {
        try {
            return tagService.findAllTags();
        } catch (ServiceException exception) {
            logger.error("unsuccessful tags selection. exit with exception");
            throw new ControllerException("can't get tags", exception);
        }
    }

    /**
     * This method returns Tag with given id.
     *
     * @param id Tag id.
     * @return Tag with given id.
     * @throws ControllerException
     */
    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") int id) throws ControllerException {
        try {
            return tagService.findTagById(id);
        } catch (ServiceException exception) {
            logger.error("unsuccessful tag search. exit with exception");
            throw new ControllerException("can't get tags", exception);
        }
    }

    /**
     * This method creates and stores Tag in database.
     *
     * @param name New tag name.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PostMapping
    public JsonAnswer createTag(@RequestParam("name") String name) {
        try {
            if (tagService.createTag(new Tag(name))) {
                logger.info("successfully created new tag");
                return new JsonAnswer(HttpStatus.OK, "successfully created new tag");
            } else {
                logger.warn("unsuccessful tag creation");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't create tag. bad request",
                        HttpStatus.BAD_REQUEST.value());
            }
        } catch (ServiceException serviceException) {
            logger.error("unsuccessful tag deletion. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't create tag. server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * This method deletes tag from database using tag id.
     *
     * @param id Delete tag id.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @DeleteMapping("/{id}")
    public JsonAnswer deleteTag(@PathVariable("id") int id) {
        try {
            if (tagService.deleteTag(id)) {
                logger.info("successfully deleted tag");
                return new JsonAnswer(HttpStatus.OK, "successfully deleted tag");
            } else {
                logger.warn("unsuccessful tag deletion");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't delete tag. bad request",
                        HttpStatus.BAD_REQUEST.value());
            }
        } catch (ServiceException exception) {
            logger.error("unsuccessful tag deletion");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't delete tag. server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
