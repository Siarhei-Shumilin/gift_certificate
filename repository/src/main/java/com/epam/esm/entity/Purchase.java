package com.epam.esm.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Purchase {
    private long id;
    private long userId;
    private long certificateId;
    private BigDecimal price;
    private LocalDateTime dateTime;

    private String certificatesName;
    private String certificateDescription;

    public Purchase() {
    }

    public Purchase(long userId, long certificateId, BigDecimal price, LocalDateTime dateTime) {
        this.userId = userId;
        this.certificateId = certificateId;
        this.price = price;
        this.dateTime = dateTime;
    }

    public String getCertificatesName() {
        return certificatesName;
    }

    public void setCertificatesName(String certificatesName) {
        this.certificatesName = certificatesName;
    }

    public String getCertificateDescription() {
        return certificateDescription;
    }

    public void setCertificateDescription(String certificateDescription) {
        this.certificateDescription = certificateDescription;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return id == purchase.id &&
                userId == purchase.userId &&
                certificateId == purchase.certificateId &&
                Objects.equals(price, purchase.price) &&
                Objects.equals(dateTime, purchase.dateTime) &&
                Objects.equals(certificatesName, purchase.certificatesName) &&
                Objects.equals(certificateDescription, purchase.certificateDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, certificateId, price, dateTime, certificatesName, certificateDescription);
    }
}