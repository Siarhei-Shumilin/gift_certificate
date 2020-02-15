package com.epam.esm.config.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {
    private String jwt;

    @JsonCreator
    public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}