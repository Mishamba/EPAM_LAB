package com.epam.esm.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.Set;

@JsonIgnoreProperties(value = "orders")
@Entity
@Table(name = "users")
public class User extends RepresentationModel<User> {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    @Column(name = "user_name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderUser")
    private Set<Order> orders;

    public User() {}

    public User(String name) {
        this.name = name;
    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return this.id.equals(user.id) &&
                this.name.equals(user.name);
    }

    @Override
    public int hashCode() {
        int prime = 3;
        int hash = id.hashCode() * prime;
        hash *= name.hashCode() * prime;
        return hash;
    }
}
