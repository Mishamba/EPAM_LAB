package com.epam.esm.model.entity;

import java.io.Serializable;

public abstract class IdEntity implements Serializable, Cloneable {
    private int id;

    public IdEntity(int id) {
        this. id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
