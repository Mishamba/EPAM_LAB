package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Entity class. Contains fields that stores Tag data.
 *
 * @version 1.0
 * @author mishamba
 */
@Entity
@Table(name = "tag")
public class Tag {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "tag_name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Certificate> certificates;

    public Tag() {}

    /**
     * Constructor without id parameter. This constructor used only in creation case.
     * id field will be automatically set by NOT_SET_ID value
     * @param name Tag name.
     *             Need to be not empty because of hibernate validation annotations
     *
     * @see ModelConstant
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

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    /**
     * Identification number getter.
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
     * Name getter.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
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
     * Default toString(). Returns tag in format:
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
