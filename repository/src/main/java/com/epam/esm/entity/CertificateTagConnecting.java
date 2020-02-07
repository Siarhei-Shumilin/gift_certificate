package com.epam.esm.entity;

import org.springframework.stereotype.Component;

@Component
public class CertificateTagConnecting {
    private long id;
    private long certificateId;
    private long tagId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(long certificateId) {
        this.certificateId = certificateId;
    }

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }
}