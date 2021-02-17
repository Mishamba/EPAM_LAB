package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import com.epam.esm.model.util.converter.LocalDateTimeConverter;
import com.epam.esm.model.util.serializer.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
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

@Entity
@Table(name = "gift_certificate")
public class Certificate {

    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty
    @Column(name = "certificate_name")
    private String name;

    @NotEmpty
    @Column(name = "certificate_description")

    private String description;

    @Positive
    @NotEmpty
    @Column(name = "price")
    private int price;

    @Positive
    @NotEmpty
    @Column(name = "duration")
    private int duration;

    @NotEmpty
    @PastOrPresent
    @JsonSerialize(using = DateTimeSerializer.class)
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "create_date")
    private LocalDateTime createDate;

    @NotEmpty
    @PastOrPresent
    @JsonSerialize(using = DateTimeSerializer.class)
    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    @UniqueElements
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Tag.class, cascade = CascadeType.DETACH)
    @JoinTable(name = "certificate_tags",
            joinColumns = @JoinColumn(name = "certificate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "orderedCertificates", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Certificate() {}
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
     * Id getter.
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Id setter. If given id is null throws NullPointerException.
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
     * Name getter.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Name setter.
     * @param name Given certificate name. Need to be not null because of hibernate validation annotations.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Description getter.
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Description setter.
     * @param description Certificate description.
     *             Need to be not empty because of hibernate validation annotation.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Price getter.
     * @return int
     */
    public int getPrice() {
        return price;
    }

    /**
     * Price setter.
     * @param price Conventional units.
     *              Need to be not empty and positive because of hibernate validation annotations.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Duration getter.
     * @return int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Duration setter.
     * @param duration Certificate shelf life duration in days.
     *                 Need to be not empty and positive because of hibernate validation annotations.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Create date as LocalDateTime getter.
     * @return LocalDateTime
     *
     * @see java.time.LocalDateTime
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Create date setter.
     * @param createDate Certificate create date.
     *                   Can't be future date and can't be empty because of hibernate validation annotations.
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Last update date getter.
     * @return LocalDateTime
     *
     * @see java.time.LocalDateTime
     */
    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * Last update date setter.
     * @param lastUpdateDate Date of last update time.
     *                       Can't be future date and can't be empty because of hibernate validation annotations.
     */
    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * Certificate Tag List getter.
     * @return List with Tag generic
     *
     * @see java.util.List
     * @see com.epam.esm.model.entity.Tag
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Certificate Tag List setter.
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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @PrePersist
    public void setDates() {
        setCreateDate(LocalDateTime.now());
        setLastUpdateDate(LocalDateTime.now());
    }

    @PreUpdate
    public void updateDate() {
        setLastUpdateDate(LocalDateTime.now());
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
     * Default toString(). Returns certificate in format:
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
