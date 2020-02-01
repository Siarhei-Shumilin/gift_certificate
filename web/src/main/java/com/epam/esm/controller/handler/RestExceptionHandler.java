package com.epam.esm.controller.handler;

import com.epam.esm.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException e) {
        ApiError apiError = new ApiError("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value()+"04");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserExistsException.class)
    protected ResponseEntity<ApiError> handleUserExistsException(UserExistsException exception) {
        ApiError apiError = new ApiError(exception.getMessage(),
                HttpStatus.BAD_REQUEST.value() + "01");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CertificateNotFoundException.class)
    protected ResponseEntity<ApiError> handleThereIsNoSuchCertificatesException(CertificateNotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(),
                HttpStatus.NOT_FOUND.value() + "01");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CertificateFieldCanNotNullException.class)
    protected ResponseEntity<ApiError> handleCertificateFieldCanNotNullException(CertificateFieldCanNotNullException exception) {
        ApiError apiError = new ApiError(exception.getMessage(),
                HttpStatus.NOT_FOUND.value() + "01");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TagNotFoundException.class)
    protected ResponseEntity<ApiError> handleTagExistException(TagNotFoundException exception) {
        ApiError apiError = new ApiError(exception.getMessage(),
                HttpStatus.NOT_FOUND.value() + "01");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND);
    }
}