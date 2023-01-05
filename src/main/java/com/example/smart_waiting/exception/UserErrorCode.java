package com.example.smart_waiting.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    EMAIL_ALREADY_EXIST("이미 존재하는 이메일 입니다.",HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXIST("이미 존재하는 핸드폰 번호입니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;
}
