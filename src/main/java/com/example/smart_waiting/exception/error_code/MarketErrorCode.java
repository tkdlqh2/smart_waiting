package com.example.smart_waiting.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MarketErrorCode {
    ALREADY_HAVE_MARKET("이미 음식점을 등록한 유저입니다.",HttpStatus.BAD_REQUEST),
    MARKET_NOT_FOUND("해당 마켓을 찾을 수 없습니다.",HttpStatus.NOT_FOUND);
    private final String message;
    private final HttpStatus httpStatus;
}
