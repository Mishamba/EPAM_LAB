package com.epam.esm.controller.handler;

import com.epam.esm.controller.json.entity.JsonError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IncorrectRequestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAnyException(Exception exception, WebRequest request) {
        JsonError error = new JsonError(HttpStatus.BAD_REQUEST, exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, new HttpHeaders(), HttpStatus.BAD_REQUEST.value());
    }
}
