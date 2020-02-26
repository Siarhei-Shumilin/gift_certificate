package com.epam.esm.config.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private String jwt;
    private String expiration;

    @JsonCreator
    public AuthenticationResponse(String jwt, String expiration) {
        this.jwt = jwt;
        this.expiration = expiration;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }
}