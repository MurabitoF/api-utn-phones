package com.example.utnphones.exception;

import lombok.Data;

@Data
public abstract class GenericException extends Exception{

    protected Integer code;
}
