package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.deserializator.CertificateDeserializer;
import com.epam.esm.model.util.serializer.DateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class. Contains fields that stores Certificate data.
 *
 * @version 1.0
 * @author mishamba
 *
 * @see com.epam.esm.model.entity.Tag
 */
@JsonDeserialize(using = CertificateDeserializer.class)
public class Certificate extends RepresentationModel<Certificate> {

    @Positive
    private int id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String description;

    @Positive
    @NotEmpty
    private int price;

    @Positive
    @NotEmpty
    private int duration;

    @NotEmpty
    @PastOrPresent
    @JsonSerialize(using = DateTimeSerializer.class)
    private LocalDateTime createDate;

    @NotEmpty
    @PastOrPresent
    @JsonSerialize(using = DateTimeSerializer.class)
    private LocalDateTime lastUpdateDate;

    @UniqueElements
    private List<Tag> tags;

    /**
     * Class constructor. This constructor don't get id parameter.
     * It used when certificate is going to be stored in database. Id will be generated in database.
     * @param name Certificate name.
     *             Need to be not empty because of hibernate validation annotation.
     * @param description Certificate description.
     *             Need to be not empty because of hibernate validation annotation.
     * @param price Conventional units.
     *              Need to be not empty and positive because of hibernate validation annotations.
     * @param duration Certificate shelf life duration in days.
     *                 Need to be not empty and positive because of hibernate validation annotations.
     * @param createDate Certificate create date.
     *                   Can't be future date and can't be empty because of hibernate validation annotations.
     * @param lastUpdateDate Date of last update time.
     *                       Can't be future date and can't be empty because of hibernate validation annotations.
     * @param tags Certificate tags list.
     *             Must contain only unique elements because of hibernate validation annotations.
     *
     * @see com.epam.esm.model.entity.Tag
     */
    @JsonCreator
    public Certificate(String name, String description, int price, int duration, LocalDateTime createDate,
                       LocalDateTime lastUpdateDate, List<Tag> tags) {
        id = ModelConstant.NOT_SET_ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    /**
     * Class constructor. This constructor gets id parameter.
     * It used when certificate is going to be updated in database.
     * @param id Identification number. Generated in database.
     *           Need to be not null because of hibernate validation annotation.
     * @param name Certificate name.
     *             Need to be not empty because of hibernate validation annotation.
     * @param description Certificate description.
     *             Need to be not empty because of hibernate validation annotation.
     * @param price Conventional units.
     *              Need to be not empty and positive because of hibernate validation annotations.
     * @param duration Certificate shelf life duration in days.
     *                 Need to be not empty and positive because of hibernate validation annotations.
     * @param createDate Certificate create date.
     *                   Can't be future date and can't be empty because of hibernate validation annotations.
     * @param lastUpdateDate Date of last update time.
     *                       Can't be future date and can't be empty because of hibernate validation annotations.
     * @param tags Certificate tags list.
     *             Must contain only unique elements because of hibernate validation annotations.
     *
     * @see com.epam.esm.model.entity.Tag
     */
    @JsonCreator
    public Certificate(int id, String name, String description, int price, int duration, LocalDateTime createDate,
                       LocalDateTime lastUpdateDate, List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        if (tags == null) {
            this.tags = new ArrayList<>();
        } else {
            this.tags = tags;
        }
    }

    /**
     * Id getter method.
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Id setter method. If given id is null throws NullPointerException.
     * @param id Identification number. Generated in database.
     *           Need to be not null because of hibernate validation annotations.
     */
    public void setId(Integer id) {
        if (id == null) {
            throw new NullPointerException("id can't be null");
        }

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
     * @param name Given certificate name. Need to be not null because of hibernate validation annotations.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description getter method.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description setter method.
     * @param description Certificate description.
     *             Need to be not empty because of hibernate validation annotation.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Price getter method.
     * @return int
     */
    public int getPrice() {
        return price;
    }

    /**
     * Price setter method.
     * @param price Conventional units.
     *              Need to be not empty and positive because of hibernate validation annotations.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Duration getter method.
     * @return int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Duration setter method.
     * @param duration Certificate shelf life duration in days.
     *                 Need to be not empty and positive because of hibernate validation annotations.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Create date as LocalDateTime getter method.
     * @return LocalDateTime
     *
     * @see java.time.LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Create date setter method.
     * @param createDate Certificate create date.
     *                   Can't be future date and can't be empty because of hibernate validation annotations.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Last update date getter method.
     * @return LocalDateTime
     *
     * @see java.time.LocalDateTime
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Last update date setter method.
     * @param lastUpdateDate Date of last update time.
     *                       Can't be future date and can't be empty because of hibernate validation annotations.
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Certificate Tag List getter method.
     * @return List with Tag generic
     *
     * @see java.util.List
     * @see com.epam.esm.model.entity.Tag
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Certificate Tag List setter method.
     * @param tags Certificate tags list.
     *             Must contain only unique elements because of hibernate validation annotations.
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Method to add Tag to Certificate Tag List.
     * @param tag Tag that will be added to tag list.
     */
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Certificate that = (Certificate) o;
        return price == that.price &&
                duration == that.duration &&
                name.equals(that.name) &&
                description.equals(that.description) &&
                createDate.equals(that.createDate) &&
                lastUpdateDate.equals(that.lastUpdateDate) &&
                tags.equals(that.tags);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        int prime = 4;
        hash += name.hashCode() * prime;
        hash += description.hashCode() * prime;
        hash *= price * prime;
        hash += duration * prime;
        hash *= createDate.hashCode() * prime;
        hash += lastUpdateDate.hashCode() * prime;

        return hash;
    }

    /**
     * Default toString() method. Returns certificate in format:
     * Certificate{name='${name}, description='${description}, price=${price}, duration=${duration},
     * createDate=${createDate}, lastUpdateDate=${lastUpdateDate}, tags=${tags}}
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Certificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", tags=" + tags +
                '}';
    }
}
