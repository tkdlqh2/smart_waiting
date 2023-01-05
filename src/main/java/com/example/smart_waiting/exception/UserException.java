package com.example.smart_waiting.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }
}
