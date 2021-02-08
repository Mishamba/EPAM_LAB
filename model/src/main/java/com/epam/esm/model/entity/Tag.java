package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.deserializator.TagDeserializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Entity class. Contains fields that stores Tag data.
 *
 * @version 1.0
 * @author mishamba
 */
@JsonDeserialize(using = TagDeserializer.class)
@Entity
public class Tag extends RepresentationModel<Tag> {

    @Positive
    @Id
    private int id;

    @NotEmpty
    private String name;

    @ManyToMany
    private Set<Certificate> certificateSet;

    /**
     * Constructor without id parameter. This constructor used only in creation case.
     * id field will be automatically set by NOT_SET_ID value
     * @param name Tag name.
     *             Need to be not empty because of hibernate validation annotations
     *
     * @see ModelConstant
     */
    @JsonCreator
    public Tag(@JsonProperty("name") String name) {
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
    @JsonCreator
    public Tag(@JsonProperty("id") int id, @JsonProperty("name") String name) {
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
