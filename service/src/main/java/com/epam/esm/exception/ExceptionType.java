package com.epam.esm.exception;

import com.epam.esm.util.ErrorMessageConstants;

public enum ExceptionType {

    CERTIFICATE_DATA_INCORRECT(ErrorMessageConstants.CERTIFICATE_INCORRECT, 400, "40001"),
    CERTIFICATE_NOT_EXISTS(ErrorMessageConstants.CERTIFICATE_NOT_EXISTS, 400, "40002"),
    TAG_DATA_INCORRECT(ErrorMessageConstants.TAG_INCORRECT, 400, "40003"),
    USER_EXISTS_EXCEPTION(ErrorMessageConstants.USER_EXISTS, 400, "40004"),
    FAILED_AUTHENTICATION(ErrorMessageConstants.FAILED_AUTHENTICATION, 403, "40301"),
    UNEXPECTED_EXCEPTION(ErrorMessageConstants.UNEXPECTED_EXCEPTION, 500, "50005");

    private final String message;
    private final int statusCode;
    private final String customCode;

    ExceptionType(String message, int statusCode, String customCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.customCode = customCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getCustomCode() {
        return customCode;
    }
}