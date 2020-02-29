package com.epam.esm.controller.handler;

import com.epam.esm.exception.ExceptionType;
import com.epam.esm.exception.GeneralException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(GeneralException.class)
    protected ResponseEntity<ApiError> handleException(GeneralException exception) {
        ExceptionType error = exception.getError();
        HttpStatus httpStatus = HttpStatus.valueOf(error.getStatusCode());
        String message = messageSource.getMessage(error.getMessage(), null, exception.getLocale());
        return new ResponseEntity<>(new ApiError(message, error.getCustomCode()), httpStatus);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException e) {
        HttpStatus httpStatus = HttpStatus.valueOf(ExceptionType.UNEXPECTED_EXCEPTION.getStatusCode());
        ApiError apiError = new ApiError(ExceptionType.UNEXPECTED_EXCEPTION.getMessage(),
                ExceptionType.UNEXPECTED_EXCEPTION.getCustomCode());
        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers,
                                                                         HttpStatus status, WebRequest request) {
        String message = ex.getMethod() + ExceptionType.METHOD_NOT_SUPPORTED.getMessage();
        ApiError apiError = new ApiError(message,
                ExceptionType.METHOD_NOT_SUPPORTED.getCustomCode());
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(ExceptionType.METHOD_NOT_SUPPORTED.getStatusCode()));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ExceptionType.NOT_HANDLER_FOUND.getMessage() + ex.getHttpMethod() + " " + ex.getRequestURL();

        ApiError apiError = new ApiError(error,
                ExceptionType.NOT_HANDLER_FOUND.getCustomCode());
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(ExceptionType.NOT_HANDLER_FOUND.getStatusCode()));
    }
}