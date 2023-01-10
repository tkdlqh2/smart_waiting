package com.example.smart_waiting.exception.exception_class;

import com.example.smart_waiting.exception.error_code.UserErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException{

    private final UserErrorCode errorCode;

    public UserException(UserErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }
}
