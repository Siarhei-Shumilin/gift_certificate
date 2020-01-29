package com.epam.esm.entity;

public class CertificateTagConnecting {
    private int id;
    private int certificateId;
    private int tagId;

    public CertificateTagConnecting(int certificateId, int tagId) {
        this.certificateId = certificateId;
        this.tagId = tagId;
    }

    public CertificateTagConnecting() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
