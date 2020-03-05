package com.epam.esm.exception;

public class GeneralException extends RuntimeException {
    private final ExceptionType error;

    public GeneralException(ExceptionType error) {
        super();
        this.error = error;
    }

    public ExceptionType getError() {
        return error;
    }
}