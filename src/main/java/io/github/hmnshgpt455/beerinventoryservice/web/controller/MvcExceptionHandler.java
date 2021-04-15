package io.github.hmnshgpt455.beerinventoryservice.web.controller;

import io.github.hmnshgpt455.beerinventoryservice.web.controller.errors.NotFoundError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ControllerAdvice
public class MvcExceptionHandler {

    public static final String CONSTRAINT_VALIDATION_FAILURE = "CONSTRAINT_VALIDATION_FAILURE";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException exception) {

        List<String> errorList = new ArrayList<>(exception.getConstraintViolations().size());
        exception.getConstraintViolations().forEach(constraintViolation -> errorList.add(constraintViolation.toString()));

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("errorUUID", UUID.randomUUID().toString());
        headers.add("errorCode", CONSTRAINT_VALIDATION_FAILURE);
        return new ResponseEntity<>(errorList, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundError> notFoundExceptionHandler(NotFoundException exception) {

        NotFoundError errorObject = new NotFoundError(exception.getErrorCode(), exception.getMessage());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("errorUUID", UUID.randomUUID().toString());
        headers.add("errorCode", exception.getErrorCode());
        return new ResponseEntity<>(errorObject, headers, HttpStatus.BAD_REQUEST);
    }
}
