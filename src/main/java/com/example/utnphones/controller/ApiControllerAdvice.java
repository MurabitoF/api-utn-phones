package com.example.utnphones.controller;

import com.example.utnphones.exception.ErrorResponse;
import com.example.utnphones.exception.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponse> notFound(GenericException ex) {
        return ResponseEntity.
                status(ex.getCode())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(ex.getCode())
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> wrongCredentials(AuthenticationException ex) {
        return ResponseEntity.status(400)
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .code(400)
                        .message("Wrong credentials")
                        .build());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> expiredToken(ExpiredJwtException ex) {
        return ResponseEntity.status(401).build();
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

}
