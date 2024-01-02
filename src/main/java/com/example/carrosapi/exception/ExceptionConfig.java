package com.example.carrosapi.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {
    @ExceptionHandler({
            EmptyResultDataAccessException.class
    })
    public ResponseEntity errorEmptyResultDataAccessException(ExceptionHandler exceptionHandler){
        return ResponseEntity.notFound().header("Message", exceptionHandler.toString()).build();
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity errorIllegalArgumentException(ExceptionHandler exceptionHandler){
        return ResponseEntity.badRequest().header("Message", exceptionHandler.toString()).build();
    }
}
