package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.deserializator.OrderDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.List;

@JsonDeserialize(using = OrderDeserializer.class)
public class Order extends RepresentationModel<Order> {
    private int id;
    private int userId;
    private int cost;
    private List<Certificate> orderedCertificates;
    private LocalDateTime orderDate;

    public Order(int userId, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.id = ModelConstant.NOT_SET_ID;
        this.userId = userId;
        this.orderedCertificates = orderedCertificates;
        this.orderDate = orderDate;
        this.cost = 0;
        for (Certificate orderedCertificate : orderedCertificates) {
            this.cost += orderedCertificate.getPrice();
        }
    }

    public Order(int id, int userId, int cost, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.id = id;
        this.userId = userId;
        this.orderedCertificates = orderedCertificates;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<Certificate> getOrderedCertificates() {
        return orderedCertificates;
    }

    public void setOrderedCertificates(List<Certificate> orderedCertificates) {
        this.orderedCertificates = orderedCertificates;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void addOrderedCertificate(Certificate certificate) {
        this.orderedCertificates.add(certificate);
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;
        return userId == order.userId &&
                orderedCertificates.equals(order.orderedCertificates) &&
                orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        int prime = 5;
        int hash = id * prime;
        hash *= orderDate.hashCode() * prime;
        hash *= orderedCertificates.hashCode() * prime;
        hash *=userId * prime;

        return hash;
    }
}
