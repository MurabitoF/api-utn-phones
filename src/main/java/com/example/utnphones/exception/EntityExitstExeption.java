package com.example.utnphones.exception;

import org.springframework.http.HttpStatus;

public class EntityExitstExeption extends GenericException {

    private final String entityName;

    public EntityExitstExeption(String entityName) {
        this.code = HttpStatus.BAD_REQUEST.value();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return entityName + " already exists";
    }
}
