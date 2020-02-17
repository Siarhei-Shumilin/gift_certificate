package com.epam.esm.exception;

import java.util.Locale;

public class GeneralException extends RuntimeException {
   private ExceptionType error;
   private Locale locale;

    public GeneralException(ExceptionType error, Locale locale) {
        this.error = error;
        this.locale = locale;
    }

    public GeneralException(String message) {
        super(message);
    }

    public ExceptionType getError() {
        return error;
    }

    public Locale getLocale() {
        return locale;
    }
}