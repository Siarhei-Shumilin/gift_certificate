package com.epam.esm.exception;

public class CertificateNotFoundException extends RuntimeException {

    public CertificateNotFoundException(String message) {
        super(message);
    }
}