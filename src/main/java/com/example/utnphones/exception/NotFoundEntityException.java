package com.example.utnphones.exception;


import org.springframework.http.HttpStatus;

public class NotFoundEntityException extends Exception {

    private final Integer code;
    private String className;

    public NotFoundEntityException(String className){
        this.code = HttpStatus.NOT_FOUND.value();
    }

    @Override
    public String getMessage() {
        return className + " not found";
    }
}
