package com.example.utnphones.controller;

import com.example.utnphones.exception.EntityExitstExeption;
import com.example.utnphones.exception.ErrorResponse;
import com.example.utnphones.exception.GenericException;
import com.example.utnphones.exception.NotFoundEntityException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice

public class ApiControllerAdvice {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundEntityException ex) {
        return ResponseEntity.
                status(ex.getCode())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> wrongCredentials() {
        return ResponseEntity.status(400)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(400)
                        .message("Wrong credentials")
                        .build());
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorResponse> wrongCallFee(SQLException ex) {
        return ResponseEntity.status(400)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(400)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> sqlException(SQLIntegrityConstraintViolationException ex){
        return ResponseEntity.status(400)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(ex.getErrorCode())
                        .message(ex.getMessage())
                        .build());
    }
}
