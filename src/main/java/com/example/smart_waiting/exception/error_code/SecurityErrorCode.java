package com.example.smart_waiting.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode {

    ACCESS_DENIED("접근이 거부된 유저입니다.", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAIL("인증이 실패했습니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;
}
