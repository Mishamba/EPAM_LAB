package com.epam.esm.controller;

import com.epam.esm.controller.constant.ErrorCodes;
import com.epam.esm.json.entity.JsonAnswer;
import com.epam.esm.json.entity.JsonError;
import com.epam.esm.model.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.exception.ServiceException;
import com.epam.esm.model.service.CertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
            // TODO: 1/13/21 send error code
        }
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable("id") int id) {
        try {
            return certificateService.findCertificateById(id);
        } catch (ServiceException exception) {
            // TODO: 1/13/21 send error code
        }
    }

    // TODO: 1/18/21 transfer tags id list
    @PostMapping
    public JsonAnswer createCertificate(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") int price, @RequestParam("duration") int duration,
                                        @RequestParam("tags") List<Integer> tags) {
        try {
            if (certificateService.createCertificate(new Certificate(name, description, price, duration,
                    LocalDateTime.now(), LocalDateTime.now(), null))) {
                logger.info("successfully created new certificate");
                return new JsonAnswer(HttpStatus.OK, "created new certificate");
            } else {
                logger.warn("unsuccessful certificate creation");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't create new create new certificate. " +
                        "bad request", ErrorCodes.USER_BAD_REQUEST);
            }
        } catch (ServiceException serviceException) {
            logger.error("unsuccessful certificate creation. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't create new certificate. server error",
                    ErrorCodes.SERVER_ERROR_CODE);
        }
    }

    // TODO: 1/18/21 transfer tags id list
    @PatchMapping("/{id}")
    public JsonAnswer updateCertificate(@PathVariable("id") int id, @RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") int price, @RequestParam("duration") int duration, @RequestParam("tags") List<Integer> tags) {
        try {
            if (certificateService.updateCertificate(new Certificate(id, name, description, price, duration, null,
                    LocalDateTime.now(), null))) {
                logger.info("successfully updated certificate");
                return new JsonAnswer(HttpStatus.OK, "updated certificate");
            } else {
                logger.warn("unsuccessful try to update certificate");
                return new JsonError(HttpStatus.BAD_REQUEST, "can't update certificate. bad request",
                        ErrorCodes.USER_BAD_REQUEST);
            }
        } catch (ServiceException exception) {
            logger.error("unsuccessful try to update certificate. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't update certificate. server error",
                    ErrorCodes.SERVER_ERROR_CODE);
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
                        ErrorCodes.SERVER_ERROR_CODE);
            }
        } catch (ServiceException exception) {
            logger.warn("unsuccessful certificate deletion. exit with exception");
            return new JsonError(HttpStatus.INTERNAL_SERVER_ERROR, "can't delete certificate. server error",
                    ErrorCodes.SERVER_ERROR_CODE);
        }
    }
}
