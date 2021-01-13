package com.epam.esm.controller;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;
import com.epam.esm.model.service.impl.CertificateServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private CertificateService certificateService = new CertificateServiceImpl();

    @GetMapping
    public List<Certificate> index() {
        try {
            return certificateService.findAllCertificates();
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code
        }
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable("id") int id) {
        Certificate certificate;
        try {
            return certificateService.findCertificateById(id);
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code
        }
    }

    @PostMapping
    public Boolean createCertificate(@ModelAttribute("certificate") Certificate certificate) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException serviceException) {
            // TODO: 1/13/21 send error code
        }
    }

    @PatchMapping("/{id}")
    public String updateCertificate(@ModelAttribute("certificate") Certificate certificate) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code
        }
    }

    @DeleteMapping("/{id}")
    public Boolean deleteCertificate(@PathVariable("id") int id) {
        try {
            // TODO: 1/13/21 send code 200
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code
        }
    }
}
