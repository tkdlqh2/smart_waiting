package com.example.smart_waiting.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WaitingsErrorCode {

    ALREADY_REGISTERED_USER("이미 대기 신청한 식당이 존재합니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;
}