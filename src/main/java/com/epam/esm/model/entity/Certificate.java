package com.epam.esm.model.entity;

import com.epam.esm.model.constant.ModelConstant;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Certificate {

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
    private LocalDateTime createDate;

    @NotEmpty
    @PastOrPresent
    private LocalDateTime lastUpdateDate;

    @UniqueElements
    private List<Tag> tags;

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

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        if (id == null) {
            throw new NullPointerException("id can't be null");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

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