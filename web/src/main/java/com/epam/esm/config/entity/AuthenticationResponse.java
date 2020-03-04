package com.epam.esm.config.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private String jwt;
    private int expirationTimeHours;

    @JsonCreator
    public AuthenticationResponse(String jwt, int expiration) {
        this.jwt = jwt;
        this.expirationTimeHours = expiration;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public int getExpirationTimeHours() {
        return expirationTimeHours;
    }

    public void setExpirationTimeHours(int expirationTimeHours) {
        this.expirationTimeHours = expirationTimeHours;
    }
}