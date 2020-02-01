package com.epam.esm.exception;

public class CertificateFieldCanNotNullException extends Exception {
    public CertificateFieldCanNotNullException() {
    }

    public CertificateFieldCanNotNullException(String message) {
        super(message);
    }
}