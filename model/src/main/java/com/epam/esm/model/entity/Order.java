package com.epam.esm.model.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private List<Certificate> orderedCertificates;
    private LocalDateTime orderDate;

    public Order(int userId, List<Certificate> orderedCertificates, LocalDateTime orderDate) {
        this.userId = userId;
        this.orderedCertificates = orderedCertificates;
        this.orderDate = orderDate;
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

    public int getCost() {
        int cost = 0;
        for (Certificate orderedCertificate : orderedCertificates) {
            cost += orderedCertificate.getPrice();
        }

        return cost;
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
