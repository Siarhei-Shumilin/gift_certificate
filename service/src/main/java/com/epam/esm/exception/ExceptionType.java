package com.epam.esm.exception;

import com.epam.esm.util.ErrorMessageConstants;

public enum ExceptionType {

    CERTIFICATE_DATA_INCORRECT(ErrorMessageConstants.CERTIFICATE_INCORRECT, 400, "40002"),
    TAG_DATA_INCORRECT(ErrorMessageConstants.TAG_INCORRECT, 400, "40002"),
    USER_EXISTS_EXCEPTION(ErrorMessageConstants.USER_EXISTS, 400, "40002");

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