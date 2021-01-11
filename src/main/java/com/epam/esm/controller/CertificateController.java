package com.epam.esm.controller;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/certificate")
public class CertificateController {
    @GetMapping
    @ResponseBody
    public List<Certificate> certificateIndex() {

    }

    @GetMapping
    @ResponseBody
    public List<Tag> tagIndex() {

    }

    @GetMapping("/{id}")
    @ResponseBody
    public Certificate getCertificate(@PathVariable("id") int id) {

    }

    // TODO: 1/11/21 add parameters
    @PostMapping
    @ResponseBody
    public Boolean createCertificate() {

    }

    // TODO: 1/11/21 add other methods
}
