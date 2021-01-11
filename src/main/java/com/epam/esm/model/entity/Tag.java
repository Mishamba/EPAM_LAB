package com.epam.esm.model.entity;

import com.epam.esm.model.constant.Constants;

public class Tag {
    private int primeForHashCode = 123;
    private int id;
    private String name;

    public Tag(String name) {
        id = Constants.NOT_SET_ID;
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
        return name.hashCode() * primeForHashCode;
    }
}
