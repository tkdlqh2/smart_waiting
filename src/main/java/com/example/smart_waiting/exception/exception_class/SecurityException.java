package com.example.smart_waiting.exception.exception_class;

import com.example.smart_waiting.exception.error_code.SecurityErrorCode;
import lombok.Getter;

@Getter
public class SecurityException extends RuntimeException{

    private final SecurityErrorCode errorCode;

    public SecurityException(SecurityErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }
}
