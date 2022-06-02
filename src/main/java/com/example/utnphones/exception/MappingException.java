package com.example.utnphones.exception;

import org.springframework.http.HttpStatus;

public class MappingException extends GenericException{
    private final String cause;

    public MappingException(String cause) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.cause = cause;
    }

    @Override
    public String getMessage() {
        return cause;
    }
}
