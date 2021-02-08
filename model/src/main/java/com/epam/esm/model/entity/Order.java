package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.deserializator.OrderDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@JsonDeserialize(using = OrderDeserializer.class)
@Entity
public class Order extends RepresentationModel<Order> {
    @Positive
    @Id
    private int id;

    @OneToOne
    private User orderUser;

    @Positive
    private int cost;

    @UniqueElements
    private List<Certificate> orderedCertificates;

    @PastOrPresent
    private LocalDateTime orderDate;

    public Order(int userId, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.id = ModelConstant.NOT_SET_ID;
        this.orderUser = null;
        this.orderedCertificates = orderedCertificates;
        this.orderDate = orderDate;
        this.cost = 0;
        for (Certificate orderedCertificate : orderedCertificates) {
            this.cost += orderedCertificate.getPrice();
        }
    }

    public Order(int id, User orderUser, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.id = id;
        this.orderUser = orderUser;
        this.orderedCertificates = orderedCertificates;
        this.orderDate = orderDate;
        this.cost = 0;
        for (Certificate orderedCertificate : orderedCertificates) {
            this.cost += orderedCertificate.getPrice();
        }
    }

    public Order(int id, User orderUser, int cost, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.id = id;
        this.orderUser = orderUser;
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

    public User getUserId() {
        return orderUser;
    }

    public void setUserId(User orderUser) {
        this.orderUser = orderUser;
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
        return orderUser.equals(order.orderUser) &&
                orderedCertificates.equals(order.orderedCertificates) &&
                orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        int prime = 5;
        int hash = id * prime;
        hash *= orderDate.hashCode() * prime;
        hash *= orderedCertificates.hashCode() * prime;
        hash *= orderUser.hashCode() * prime;

        return hash;
    }
}
