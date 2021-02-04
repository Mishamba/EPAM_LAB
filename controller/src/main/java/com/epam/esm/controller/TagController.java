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
     * Method returns all Tags stored in database.
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
     * Method returns Tag with given id.
     *
     * @param id Tag id.
     * @return Tag with given id.
     * @throws ControllerException
     */
    @GetMapping("/get/{id}")
    public Tag getTagById(@PathVariable("id") int id) throws ControllerException {
        try {
            return tagService.findTagById(id);
        } catch (ServiceException exception) {
            logger.error("unsuccessful tag search. exit with exception");
            throw new ControllerException("can't get tags", exception);
        }
    }

    /**
     * Method creates and stores Tag in database.
     *
     * @param name New tag name.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PostMapping("/create")
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
     * Method deletes tag from database using tag id.
     *
     * @param id Delete tag id.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @DeleteMapping("/delete/{id}")
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
