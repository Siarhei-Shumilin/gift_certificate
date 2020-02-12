package com.epam.esm.controller.handler;

import com.epam.esm.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${error.code.not.found}")
    private String errorCodeNotFound;
    @Value("${error.code.bad.request}")
    private String errorCodeBadRequest;
    @Value("${error.code.server}")
    private String errorCodeServer;

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value() + errorCodeNotFound);
        return new ResponseEntity<>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralException.class)
    protected ResponseEntity<ApiError> handleException(GeneralException exception){
        ResponseEntity<ApiError> responseEntity = null;
        if (exception instanceof CertificateDataIncorrectException || exception instanceof TagDataIncorrectException
                || exception instanceof UserExistsException){
            String status = HttpStatus.BAD_REQUEST.value() + errorCodeBadRequest;
            responseEntity = new ResponseEntity<>(new ApiError(exception.getMessage(), status), HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException e) {
        ApiError apiError = new ApiError("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value() + errorCodeServer);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}