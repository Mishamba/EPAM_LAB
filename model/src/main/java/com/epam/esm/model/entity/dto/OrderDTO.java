package com.epam.esm.model.entity.dto;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.util.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO extends RepresentationModel<OrderDTO> {
    private final int id;
    private final UserDTO user;
    @JsonSerialize(using = DateTimeSerializer.class)
    private final LocalDateTime orderDate;
    private final List<CertificateDTO> certificates;

    protected OrderDTO(int id, UserDTO user, LocalDateTime orderDate, List<CertificateDTO> certificates) {
        this.id = id;
        this.user = user;
        this.orderDate = orderDate;
        this.certificates = certificates;
    }

    public static OrderDTO createFromOrder(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDTO(order.getId(), UserDTO.createFromUser(order.getUser()), order.getOrderDate(),
                order.getOrderedCertificates().stream().map(CertificateDTO::createFromCertificate).
                        collect(Collectors.toList()));
    }

    public int getId() {
        return id;
    }

    public UserDTO getUser() {
        return user;
    }

    public List<CertificateDTO> getCertificates() {
        return certificates;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}
