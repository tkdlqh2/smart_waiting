package com.example.smart_waiting.exception.exception_class;

import com.example.smart_waiting.exception.error_code.WaitingsErrorCode;
import lombok.Getter;

@Getter
public class WaitingsException extends RuntimeException {

    private final WaitingsErrorCode errorCode;

    public WaitingsException(WaitingsErrorCode code){
        super(code.getMessage());
        this.errorCode = code;
    }
}
