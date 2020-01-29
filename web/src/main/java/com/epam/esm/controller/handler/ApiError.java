package com.epam.esm.controller.handler;

public class ApiError {
    private String status;
    private String message;

    public ApiError(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public ApiError(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}