package com.epam.esm.controller.impl;

import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.controller.json.entity.JsonAnswer;
import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.OrderSortParametersConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.constant.UserSortParametersConstant;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.entity.dto.CertificateDTO;
import com.epam.esm.model.entity.dto.OrderDTO;
import com.epam.esm.model.entity.dto.TagDTO;
import com.epam.esm.model.entity.dto.UserDTO;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping("/get/all")
    public List<UserDTO> findAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = UserSortParametersConstant.SORT_BY_ID) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType) throws ControllerException {
        try {
            List<UserDTO> users = userService.findAllUsers(new PaginationData(sortBy, sortType, pageNumber));
            for (UserDTO user : users) {
                addLinkToUser(user);
            }

            return users;
        } catch (ServiceException exception) {
            logger.error("can't send users");
            throw new ControllerException("can't send users", exception);
        }
    }

    private void addLinkToUser(UserDTO user) throws ControllerException {
        user.add(linkTo(methodOn(CertificateController.class).index(1,
                CertificateSortParametersConstant.SORT_BY_NAME, SortOrderConstant.ASC_SORT_TYPE)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).findUserOrders(1,
                UserSortParametersConstant.SORT_BY_ID, SortOrderConstant.ASC_SORT_TYPE, 1)).withSelfRel());
    }

    @GetMapping("/get/user_orders")
    public List<OrderDTO> findUserOrders(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = OrderSortParametersConstant.SORT_BY_NAME) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType,
            @RequestParam("user_id") int userId) throws ControllerException {
        try {
            return orderService.findUserOrders(userId, new PaginationData(sortBy, sortType, pageNumber));
        } catch (ServiceException exception) {
            logger.error("can't send user orders");
            throw new ControllerException("can't send user orders", exception);
        }
    }

    @GetMapping("/widely_used_tag")
    public TagDTO widelyUsedTag() {
        return userService.userWidelyUsedTag();
    }

    @PostMapping("/create/order")
    public JsonAnswer createOrder(@RequestParam("user_id") int userId,
                                  @RequestParam("certificate_id") int[] orderedCertificateIds) throws ControllerException {
        orderService.createOrder(userId, orderedCertificateIds);
        return new JsonAnswer(HttpStatus.OK, "created order");
    }
}
