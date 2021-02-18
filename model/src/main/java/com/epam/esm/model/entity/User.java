package com.epam.esm.model.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import javax.persistence.*;
import java.util.List;

// TODO: 2/17/21 add password
@Entity
@Table(name = "users")
public class User {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]")
    private String firstName;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]")
    private String lastName;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotEmpty
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderUser")
    private List<Order> orders;

    public User() {}

    public User(@NotEmpty @Email String email,
                @NotEmpty @Pattern(regexp = "[a-zA-Z]") String firstName,
                @NotEmpty @Pattern(regexp = "[a-zA-Z]") String lastName,
                @NotEmpty String password,
                @NotEmpty Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public User(@Positive Integer id, @NotEmpty @Email String email,
                @NotEmpty @Pattern(regexp = "[a-zA-Z]") String firstName,
                @NotEmpty @Pattern(regexp = "[a-zA-Z]") String lastName,
                @NotEmpty String password,
                @NotEmpty Role role) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
                this.email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int prime = 3;
        int hash = id.hashCode() * prime;
        hash *= email.hashCode() * prime;
        return hash;
    }
}
