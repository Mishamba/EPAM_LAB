package com.epam.esm.controller;

import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.controller.json.entity.JsonError;
import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author mishamba
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;
    private final Logger logger = Logger.getLogger(CertificateController.class);

    /**
     * Constructor. Gets parameters from Spring using @Autowired.
     *
     * @param certificateService Service layer component
     *
     * @see com.epam.esm.service.impl.CertificateServiceImpl
     */
    @Autowired
    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Returns all Certificates stored in database.
     *
     * @return All certificates
     * @throws ControllerException
     */
    @GetMapping("/get/all")
    public List<Certificate> index(@RequestParam(value = "page_number", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = CertificateSortParametersConstant.SORT_BY_DATE) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType)
            throws ControllerException {
        try {
            List<Certificate> certificates = certificateService.
                    findAllCertificates(new PaginationData(sortBy, sortType, pageNumber));
            for (Certificate certificate : certificates) {
                addLinksToCertificate(certificate);
            }
            return certificates;
        } catch (ServiceException exception) {
            logger.error("can't get certificates");
            throw new ControllerException("can't get certificates", exception);
        }
    }

    /**
     * Find certificates with similar name or description.
     *
     * @param sortBy Field to sort by. Available variants are: NAME, DATE (createDate).
     * @param sortType Sort order variant. Available variants are: ASC, DESC (as in SQL).
     * @param certificateName Certificate name to find by.
     * @param description Description to find by.
     * @return Certificates with similar name of description.
     * @throws ControllerException In case if some problems method throws ControllerException and user will get
     *                             JSON error answer.
     */

    @GetMapping("/get/by/name_and_description")
    public List<Certificate> findCertificateByNameAndDescription(
            @RequestParam(value = "page_number", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = CertificateSortParametersConstant.SORT_BY_DATE) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType,
            @RequestParam(value = "name", defaultValue = ModelConstant.STRANGE_SYMBOL) String certificateName,
            @RequestParam(value = "description", defaultValue = ModelConstant.STRANGE_SYMBOL) String description)
            throws ControllerException {
        try {
            List<Certificate> certificates = certificateService.
                    findCertificatesByNameAndDescription(certificateName, description,
                            new PaginationData(sortBy, sortType, pageNumber));
            for (Certificate certificate : certificates) {
                addLinksToCertificate(certificate);
            }
            return certificates;
        } catch (ServiceException exception) {
            logger.error("can't get certificates", exception);
            throw new ControllerException("can't get certificates", exception);
        }
    }

    /**
     * Find certificates with given tag.
     *
     * @param sortBy Field to sort by. Available variants are: NAME, DATE (createDate).
     * @param sortType Sort order variant. Available variants are: ASC, DESC (as in SQL).
     * @param tags Tags to find with.
     * @return Certificate with given tag.
     * @throws ControllerException In case if some problems method throws ControllerException and user will get
     *                             JSON error answer.
     */
    @GetMapping("/get/by/tags")
    public List<Certificate> findCertificateByTag(
            @RequestParam(value = "page_number", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = CertificateSortParametersConstant.SORT_BY_DATE) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType,
            @RequestBody List<Tag> tags) throws ControllerException {
        try {
            List<Certificate> certificates = new ArrayList<>();
            for (Tag tag : tags) {
                certificates.addAll(certificateService.findCertificatesByTag(tag.getName(),
                        new PaginationData(sortBy, sortType, pageNumber)));
            }

            for (Certificate certificate : certificates) {
                addLinksToCertificate(certificate);
            }
            return certificates;
        } catch (ServiceException exception) {
            logger.error("can't get certificates");
            throw new ControllerException("can't get certificates");
        }
    }

    /**
     * Returns Certificate with given id.
     *
     * @param id Certificate id.
     * @return Certificate with given id.
     * @throws ControllerException In case if some problems method throws ControllerException and user will get
     *                             JSON error answer.
     */
    @GetMapping("/get/{id}")
    public Certificate findCertificate(@PathVariable("id") int id) throws ControllerException {
        Certificate certificate = certificateService.findCertificateById(id);
        if (certificate != null) {
            addLinksToCertificate(certificate);
        } else {
            throw new ControllerException("can't find certificate");
        }
        return certificate;
    }

    private void addLinksToCertificate(Certificate certificate) throws ControllerException {
        for (Tag tag : certificate.getTags()) {
            certificate.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withSelfRel());
        }
    }

    /**
     * Creates certificate using given parameters.
     *
     * @param newCertificate Certificate to create.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PostMapping("/create")
    public JsonAnswer createCertificate(@RequestBody Certificate newCertificate) {
        certificateService.createCertificate(newCertificate);
        return new JsonAnswer(HttpStatus.OK, "created new certificate");
    }

    /**
     * Updates Certificate data.
     *
     * @param id Updated certificate id.
     * @param price Updated certificate price.
     * @param duration Updated certificate duration.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PatchMapping("/update/{id}")
    public JsonAnswer updateCertificate(@PathVariable("id") int id,
                                        @RequestParam(value = "price", defaultValue = "-1") int price,
                                        @RequestParam(value = "duration", defaultValue = "-1") int duration) {
        if (price > 0 && duration < 0) {
            certificateService.updateCertificatePrice(id, price);
        } else if (price < 0 && duration > 0) {
            certificateService.updateCertificateDuration(id, duration);
        } else {
            return new JsonError(HttpStatus.BAD_REQUEST, "wrong parameters", 400);
        }

        return new JsonAnswer(HttpStatus.OK, "updated certificate");
    }

    /**
     * Deletes certificate with given id.
     *
     * @param id Certificate id to delete.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @DeleteMapping("/delete/{id}")
    public JsonAnswer deleteCertificate(@PathVariable("id") int id) {
        certificateService.deleteCertificate(id);
        return new JsonAnswer(HttpStatus.OK, "deleted certificate");
    }
}
