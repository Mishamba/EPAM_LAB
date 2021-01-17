package com.epam.esm.controller;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.TagService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
            // TODO: 1/13/21 send error code and log
        }
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable("id") int id) {
        Tag tag;
        try {
            return tagService.findTagById(id);
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @PostMapping
    public Boolean createTag(@ModelAttribute("tag") Tag tag) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException serviceException) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @DeleteMapping("/{id}")
    public Boolean deleteTag(@PathVariable("id") int id) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }
}
