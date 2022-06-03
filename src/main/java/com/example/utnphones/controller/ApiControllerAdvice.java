package com.example.utnphones.controller;

import com.example.utnphones.exception.ErrorResponse;
import com.example.utnphones.exception.NotFoundEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice

public class ApiControllerAdvice {

    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundEntityException ex) {
        return ResponseEntity.
                status(ex.getCode())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .build());
    }

}
