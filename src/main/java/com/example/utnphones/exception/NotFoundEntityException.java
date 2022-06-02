package com.example.utnphones.exception;


import org.springframework.http.HttpStatus;

public class NotFoundEntityException extends GenericException {
    private final String className;

    public NotFoundEntityException(String className){
        this.code = HttpStatus.NOT_FOUND.value();
        this.className = className;
    }

    @Override
    public String getMessage() {
        return className + " not found";
    }
}
