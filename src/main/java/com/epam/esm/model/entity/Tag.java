package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public class Tag {

    @Positive
    private int id;

    @NotEmpty
    private String name;

    public Tag(String name) {
        id = ModelConstant.NOT_SET_ID;
        this.name = name;
    }

    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }

        Tag tag = (Tag) o;
        return name.equals(tag.name);
    }

    @Override
    public int hashCode() {
        int primeForHashCode = 123;
        return name.hashCode() * primeForHashCode;
    }
}
