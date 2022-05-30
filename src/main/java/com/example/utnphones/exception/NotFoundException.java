package com.example.utnphones.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {

    private final Integer code;
    private String className;

    public NotFoundException(String className){
        this.code = HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return className + " not found";
    }
}
