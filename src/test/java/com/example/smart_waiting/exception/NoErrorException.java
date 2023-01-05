package com.example.smart_waiting.exception;

public class NoErrorException extends RuntimeException{

    private static final String ERROR_MESSAGE = "에러가 발생하지 않음";

    public NoErrorException(){
        super(ERROR_MESSAGE);
    }

    public String getMessage(){
        return ERROR_MESSAGE;
    }
}
