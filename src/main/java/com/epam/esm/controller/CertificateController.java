package com.epam.esm.controller;

import com.epam.esm.model.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;
    private final Logger logger = Logger.getLogger(CertificateDaoImpl.class);

    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<Certificate> index() {
        try {
            return certificateService.findAllCertificates();
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable("id") int id) {
        try {
            return certificateService.findCertificateById(id);
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @PostMapping
    public Boolean createCertificate(@ModelAttribute("certificate") Certificate certificate) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException serviceException) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @PatchMapping("/{id}")
    public String updateCertificate(@ModelAttribute("certificate") Certificate certificate) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCertificate(@PathVariable("id") int id) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code and log
        }
    }
}
