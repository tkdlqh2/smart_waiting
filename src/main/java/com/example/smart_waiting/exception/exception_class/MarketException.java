package com.example.smart_waiting.exception.exception_class;

import com.example.smart_waiting.exception.error_code.MarketErrorCode;
import lombok.Getter;

@Getter
public class MarketException extends RuntimeException {

    private final MarketErrorCode errorCode;
    public MarketException(MarketErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
