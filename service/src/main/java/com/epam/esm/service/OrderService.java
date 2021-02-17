package com.epam.esm.service;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.dto.OrderDTO;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.model.util.entity.PaginationData;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findUserOrders(int userId, PaginationData paginationData) throws ServiceException;
    void createOrder(int userId, int[] orderedCertificateIds);
}
