package com.epam.esm.controller;

import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.controller.json.entity.JsonError;
import com.epam.esm.dao.impl.CertificateDaoImpl;
import com.epam.esm.model.constant.Constant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.model.util.comparator.CertificateComparatorFactory;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

/**
 * @version 1.0
 * @author mishamba
 */
@RestController
@RequestMapping("/certificate")
public class CertificateController {
    private final CertificateService certificateService;
    private final CertificateComparatorFactory comparatorFactory;
    private final Logger logger = Logger.getLogger(CertificateController.class);

    /**
     * Constructor. Gets parameters from Spring using @Autowired.
     *
     * @param certificateService Service layer component
     *
     * @see com.epam.esm.service.impl.CertificateServiceImpl
     */
    @Autowired
    public CertificateController(CertificateService certificateService, CertificateComparatorFactory comparatorFactory) {
        this.certificateService = certificateService;
        this.comparatorFactory = comparatorFactory;
    }

    /**
     * Returns all Certificates stored in database.
     *
     * @return All certificates
     * @throws ControllerException
     */
    @GetMapping("/get/all")
    public List<Certificate> index(@RequestParam(name = "sort_by", required = false) String sortBy,
                                   @RequestParam(name = "sort_type", required = false) String sortType)
            throws ControllerException {
        try {
            List<Certificate> certificates = certificateService.findAllCertificates();
            sortCertificateList(certificates, sortBy, sortType);
            return certificates;
        } catch (ServiceException exception) {
            logger.error("can't get certificates");
            throw new ControllerException("can't get certificates", exception);
        }
    }

    /**
     * Method find certificates with similar name or description.
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
            @RequestParam(name = "sort_by", required = false) String sortBy,
            @RequestParam(name = "sort_type", required = false) String sortType,
            @RequestParam(value = "name", required = false) String certificateName,
            @RequestParam(value = "description", required = false) String description) throws ControllerException {
        try {
            List<Certificate> certificates = certificateService.
                    findCertificatesByNameAndDescription(certificateName, description);
            sortCertificateList(certificates, sortBy, sortType);
            return certificates;
        } catch (ServiceException exception) {
            logger.error("can't get certificates", exception);
            throw new ControllerException("can't get certificates", exception);
        }
    }

    /**
     * Method find certificates with given tag.
     *
     * @param sortBy Field to sort by. Available variants are: NAME, DATE (createDate).
     * @param sortType Sort order variant. Available variants are: ASC, DESC (as in SQL).
     * @param tagName Tag to find with.
     * @return Certificate with given tag.
     * @throws ControllerException In case if some problems method throws ControllerException and user will get
     *                             JSON error answer.
     */
    @GetMapping("/get/by/tag")
    public List<Certificate> findCertificateByTag(@RequestParam(name = "sort_by", required = false) String sortBy,
                                                  @RequestParam(name = "sort_type", required = false) String sortType,
                                                  @RequestParam("tag_name") String tagName) throws ControllerException {
        try {
            List<Certificate> certificates = certificateService.findCertificatesByTag(tagName);
            sortCertificateList(certificates, sortBy, sortType);
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
        try {
            return certificateService.findCertificateById(id);
        } catch (ServiceException exception) {
            throw new ControllerException("can't get certificate", exception);
        }
    }

    private void sortCertificateList(List<Certificate> certificates, String sortBy, String sortType) {
        if (sortBy == null || !(sortBy.equals(Constant.SORT_BY_NAME) || sortBy.equals(Constant.SORT_BY_DATE))) {
            sortBy = Constant.SORT_BY_NAME;
        }

        if (sortType == null ||
                !(sortType.equals(Constant.DESC_SORT_TYPE) || sortType.equals(Constant.ASC_SORT_TYPE))) {
            sortType = Constant.ASC_SORT_TYPE;
        }

        Comparator<Certificate> certificateComparator = getComparator(sortBy, sortType);
        certificates.sort(certificateComparator);
    }

    private Comparator<Certificate> getComparator(String sortBy, String sortType) {
        return comparatorFactory.getComparator(sortBy, sortType);
    }

    /**
     * Creates certificate using given parameters.
     *
     * @param name New certificate name.
     * @param description New certificate description.
     * @param price New certificate price.
     * @param duration New certificate duration.
     * @param tags New certificate tags.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PostMapping("/create")
    public JsonAnswer createCertificate(@RequestParam("name") String name,
                                        @RequestParam("description") String description,
                                        @RequestParam("price") int price, @RequestParam("duration") int duration,
                                        @RequestParam("tags") List<Tag> tags) {
        try {
            LocalDateTime now = LocalDateTime.now();
            if (certificateService.createCertificate(
                    new Certificate(name, description, price, duration, now, now, tags))) {
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

    /**
     * Updates Certificate data.
     *
     * @param id Updated certificate id.
     * @param name Updated certificate name.
     * @param description Update certificate description.
     * @param price Updated certificate price.
     * @param duration Updated certificate duration.
     * @param tags Updated certificate tags.
     * @return JsonError of JsonAnswer with operation result status.
     *
     * @see com.epam.esm.controller.json.entity.JsonAnswer
     * @see com.epam.esm.controller.json.entity.JsonError
     */
    @PatchMapping("/update/{id}")
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
