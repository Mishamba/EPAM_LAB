package com.epam.esm.controller;

import com.epam.esm.controller.constant.ErrorCodes;
import com.epam.esm.json.entity.JsonAnswer;
import com.epam.esm.json.entity.JsonError;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;
    private final Logger logger = Logger.getLogger(TagController.class);

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> index() {
        try {
            return tagService.findAllTags();
        } catch (ServiceException exception) {
            logger.error("unsuccessful tags selection. exit with exception");
            // TODO: 1/13/21 send error code
        }
    }

    @GetMapping("/{id}")
    public Tag getTagById(@PathVariable("id") int id) {
        try {
            return tagService.findTagById(id);
        } catch (ServiceException exception) {
            logger.error("unsuccessful tag search. exit with exception");
            // TODO: 1/13/21 send error code
        }
    }

    @PostMapping
    public JsonAnswer createTag(@ModelAttribute("tag") Tag tag) {
        try {
            if (tagService.createTag(tag)) {
                logger.info("successfully created new tag");
                return new JsonAnswer(HttpStatus.OK, "successfully created new tag");
            } else {
                logger.warn("unsuccessful tag creation");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't create tag. bad request",
                        ErrorCodes.USER_BAD_REQUEST);
            }
        } catch (ServiceException serviceException) {
            logger.error("unsuccessful tag deletion. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't create tag. server error",
                    ErrorCodes.SERVER_ERROR_CODE);
        }
    }

    @DeleteMapping("/{id}")
    public JsonAnswer deleteTag(@PathVariable("id") int id) {
        try {
            if (tagService.deleteTag(id)) {
                logger.info("successfully deleted tag");
                return new JsonAnswer(HttpStatus.OK, "successfully deleted tag");
            } else {
                logger.warn("unsuccessful tag deletion");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't delete tag. bad request",
                        ErrorCodes.USER_BAD_REQUEST);
            }
        } catch (ServiceException exception) {
            logger.error("unsuccessful tag deletion");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't delete tag. server error",
                    ErrorCodes.SERVER_ERROR_CODE);
        }
    }
}
