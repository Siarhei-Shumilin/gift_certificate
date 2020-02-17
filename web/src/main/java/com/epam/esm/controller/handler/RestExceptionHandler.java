package com.epam.esm.controller.handler;

import com.epam.esm.exception.*;
import com.epam.esm.exception.ExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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
    @Autowired
    private MessageSource messageSource;
    @Value("${error.code.not.found}")
    private String errorCodeNotFound;

    @ExceptionHandler(GeneralException.class)
    protected ResponseEntity<ApiError> handleException(GeneralException exception) {
        ExceptionType error = exception.getError();
        HttpStatus httpStatus = HttpStatus.valueOf(error.getStatusCode());
        return new ResponseEntity<>(
                new ApiError(messageSource.getMessage(error.getMessage(), null, exception.getLocale()),
                        error.getCustomCode()), httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value() + errorCodeNotFound);
        return new ResponseEntity<>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(ExceptionType.UNEXPECTED_EXCEPTION.getStatusCode());
        ApiError apiError = new ApiError(ExceptionType.UNEXPECTED_EXCEPTION.getMessage(),
                ExceptionType.UNEXPECTED_EXCEPTION.getCustomCode());
        return new ResponseEntity<>(apiError, httpStatus);
    }
}