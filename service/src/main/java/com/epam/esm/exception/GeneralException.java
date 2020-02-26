package com.epam.esm.exception;

import java.util.Locale;

public class GeneralException extends RuntimeException {
   private final ExceptionType error;
   private final Locale locale;

    public GeneralException(ExceptionType error, Locale locale) {
        super();
        this.error = error;
        this.locale = locale;
    }

    public ExceptionType getError() {
        return error;
    }

    public Locale getLocale() {
        return locale;
    }
}