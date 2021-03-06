package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.converter.LocalDateTimeConverter;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "users_id", nullable = false)
    private User orderUser;

    @Positive
    @Column(name = "cost")
    private int cost;

    @UniqueElements
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Certificate.class, cascade = CascadeType.DETACH)
    @JoinTable(name = "certificate_orders",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "certificate_id", referencedColumnName = "id")
    )
    private List<Certificate> orderedCertificates;

    @PastOrPresent
    @Column(name = "order_date")
    @CreatedDate
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime orderDate;

    public Order() {}

    public Order(User orderUser, @UniqueElements List<Certificate> orderedCertificates) {
        this.orderUser = orderUser;
        this.orderedCertificates = orderedCertificates;
    }

    public Order(List<Certificate> orderedCertificates, LocalDateTime orderDate) {
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

    public User getUser() {
        return orderUser;
    }

    public void setUser(User orderUser) {
        this.orderUser = orderUser;
    }

    public void addOrderedCertificate(Certificate certificate) {
        this.orderedCertificates.add(certificate);
    }

    public int getCost() {
        return this.cost;
    }

    @PreUpdate
    @PrePersist
    public void calculateCost() {
        this.cost = 0;
        for (Certificate certificate : orderedCertificates) {
            this.cost += certificate.getPrice();
        }
        this.orderDate = LocalDateTime.now();
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
