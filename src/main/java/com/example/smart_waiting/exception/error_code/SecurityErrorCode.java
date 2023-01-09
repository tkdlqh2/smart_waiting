package com.example.smart_waiting.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SecurityErrorCode {

    ACCESS_DENIED("이미 존재하는 이메일 입니다.", HttpStatus.BAD_REQUEST),
    AUTHENTICATION_FAIL("이미 존재하는 핸드폰 번호입니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;
}
