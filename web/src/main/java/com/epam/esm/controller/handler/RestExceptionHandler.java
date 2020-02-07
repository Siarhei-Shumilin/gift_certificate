package com.epam.esm.controller.handler;

import com.epam.esm.exception.*;
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

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError =
                new ApiError(ex.getLocalizedMessage(), HttpStatus.NOT_FOUND.value()+"01");
        return new ResponseEntity<>(
                apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralException.class)
    protected ResponseEntity<ApiError> handleException(GeneralException exception){
        String status = null;
        if (exception instanceof CertificateDataIncorrectException || exception instanceof TagDataIncorrectException
                || exception instanceof UserExistsException){
            status = HttpStatus.BAD_REQUEST.value() + "02";
        }
        if (exception instanceof CertificateNotFoundException || exception instanceof TagNotFoundException ){
           status =  HttpStatus.NOT_FOUND.value() + "01";
        }
        ApiError apiError = new ApiError(exception.getMessage(), status);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiError> handleRunTimeException(RuntimeException e) {
        ApiError apiError = new ApiError("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR.value()+"04");
        return new ResponseEntity<ApiError>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}