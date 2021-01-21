package com.epam.esm.controller;

import com.epam.esm.json.entity.JsonAnswer;
import com.epam.esm.json.entity.JsonError;
import com.epam.esm.model.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.exception.ControllerException;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is controller layer class. Class used everytime when url pattern starts with "/certificate/*" part
 * Each method call depends on used http method and url path.
 *
 * @version 1.0
 * @author mishamba
 */

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
    public List<Certificate> index() throws ControllerException {
        try {
            return certificateService.findAllCertificates();
        } catch (ServiceException exception) {
            throw new ControllerException("can't get certificates", exception);
        }
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable("id") int id) throws ControllerException {
        try {
            return certificateService.findCertificateById(id);
        } catch (ServiceException exception) {
            throw new ControllerException("can't get certfiicate", exception);
        }
    }

    @PostMapping
    public JsonAnswer createCertificate(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") int price, @RequestParam("duration") int duration,
                                        @RequestParam("tags") List<Tag> tags) {
        try {
            if (certificateService.createCertificate(new Certificate(name, description, price, duration,
                    LocalDateTime.now(), LocalDateTime.now(), tags))) {
                logger.info("successfully created new certificate");
                return new JsonAnswer(HttpStatus.OK, "created new certificate");
            } else {
                logger.warn("unsuccessful certificate creation");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't create new create new certificate. " +
                        "bad request", HttpStatus.BAD_REQUEST.value());
            }
        } catch (ServiceException serviceException) {
            logger.error("unsuccessful certificate creation. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't create new certificate. server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @PatchMapping("/{id}")
    public JsonAnswer updateCertificate(@PathVariable("id") int id, @RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") int price, @RequestParam("duration") int duration,
                                        @RequestParam("tags") List<Tag> tags) {
        try {
            if (certificateService.updateCertificate(new Certificate(id, name, description, price, duration, null,
                    LocalDateTime.now(), tags))) {
                logger.info("successfully updated certificate");
                return new JsonAnswer(HttpStatus.OK, "updated certificate");
            } else {
                logger.warn("unsuccessful try to update certificate");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't update certificate. bad request",
                        HttpStatus.BAD_REQUEST.value());
            }
        } catch (ServiceException exception) {
            logger.error("unsuccessful try to update certificate. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't update certificate. server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @DeleteMapping("/{id}")
    public JsonAnswer deleteCertificate(@PathVariable("id") int id) {
        try {
            if (certificateService.deleteCertificate(id)) {
                logger.info("successful delete of certificate");
                return new JsonAnswer(HttpStatus.OK, "deleted certificate");
            } else {
                logger.warn("unsuccessful certificate deletion");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't delete certificate. bad request",
                        HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } catch (ServiceException exception) {
            logger.warn("unsuccessful certificate deletion. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't delete certificate. server error",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
