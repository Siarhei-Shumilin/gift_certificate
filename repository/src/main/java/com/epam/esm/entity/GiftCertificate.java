package com.epam.esm.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Component
public class GiftCertificate implements Serializable {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;
    private List<Tag> tagList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return id == that.id &&
                duration == that.duration &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(tagList, that.tagList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, createDate, lastUpdateDate, duration, tagList);
    }
}