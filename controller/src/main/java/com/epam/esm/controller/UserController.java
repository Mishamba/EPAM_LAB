package com.epam.esm.controller;

import com.epam.esm.model.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    // TODO: 2/2/21 finish
    @GetMapping("/get/all")
    public HttpEntity<User> getUsers(@RequestParam(value = "page", required = false) Integer pageNumber) {
        return null;
    }
}
