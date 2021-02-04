package com.epam.esm.controller;

import com.epam.esm.controller.exception.ControllerException;
import com.epam.esm.model.constant.CertificateSortParametersConstant;
import com.epam.esm.model.constant.SortOrderConstant;
import com.epam.esm.model.constant.UserSortParametersConstant;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<User> findAllUsers(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = CertificateSortParametersConstant.SORT_BY_DATE) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType) throws ControllerException {
        try {
            List<User> users = userService.findAllUsers(new PaginationData(sortBy, sortType, pageNumber));
            for (User user : users) {
                addLinkToUser(user);
            }

            return users;
        } catch (ServiceException exception) {
            logger.error("can't send users");
            throw new ControllerException("can't send users", exception);
        }
    }

    private void addLinkToUser(User user) throws ControllerException {
        user.add(linkTo(methodOn(CertificateController.class).index(1,
                CertificateSortParametersConstant.SORT_BY_NAME, SortOrderConstant.ASC_SORT_TYPE)).withSelfRel());
        // TODO: 2/4/21 get user id from session
        user.add(linkTo(methodOn(UserController.class).findUserOrders(1,
                UserSortParametersConstant.SORT_BY_ID, SortOrderConstant.ASC_SORT_TYPE, 1)).withSelfRel());
    }

    @GetMapping("/get/user_orders")
    public List<Order> findUserOrders(
            @RequestParam(value = "page", defaultValue = "1") int pageNumber,
            @RequestParam(name = "sort_by", defaultValue = CertificateSortParametersConstant.SORT_BY_DATE) String sortBy,
            @RequestParam(name = "sort_type", defaultValue = SortOrderConstant.ASC_SORT_TYPE) String sortType,
            @SessionAttribute("userId") int userId) throws ControllerException {
        try {
            return orderService.findUserOrders(userId, new PaginationData(sortBy, sortType, pageNumber));
        } catch (ServiceException exception) {
            logger.error("can't send user orders");
            throw new ControllerException("can't send user orders", exception);
        }
    }

}
