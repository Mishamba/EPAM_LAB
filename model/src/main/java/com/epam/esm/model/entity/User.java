package com.epam.esm.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

public class User extends RepresentationModel<User> {

    @Positive
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;

    @JsonCreator
    public User(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonCreator
    public User(@JsonProperty("id") Integer id, @JsonProperty("name") String name) {
        this.id = id;
        this.name = name;
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
