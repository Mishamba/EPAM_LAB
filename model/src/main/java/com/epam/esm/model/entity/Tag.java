package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

/**
 * Entity class. Contains fields that stores Tag data.
 *
 * @version 1.0
 * @author mishamba
 */
public class Tag {

    @Positive
    private int id;

    @NotEmpty
    private String name;

    /**
     * Constructor without id parameter. This constructor used only in creation case.
     * id field will be automatically set by NOT_SET_ID value
     * @param name Tag name.
     *             Need to be not empty because of hibernate validation annotations
     *
     * @see com.epam.esm.model.constant.ModelConstant
     */
    public Tag(String name) {
        id = ModelConstant.NOT_SET_ID;
        this.name = name;
    }

    /**
     * Constructor.
     * @param id Generated in database identification number.
     *           Need to be positive because of hibernate annotation.
     * @param name Tag name.
     *             Need to be not empty because of hibernate annotation.
     */
    public Tag(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Identification number getter method.
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Identification number gette.
     * @param id Generated in database identification number.
     *           Need to be positive because of hibernate annotation.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Name getter method.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter method.
     * @param name Tag name.
     *             Need to be not empty because of hibernate annotation.
     */
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

    /**
     * Default toString() method. Returns tag in format:
     * Tag{id=${id}, name='${name}'}
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}