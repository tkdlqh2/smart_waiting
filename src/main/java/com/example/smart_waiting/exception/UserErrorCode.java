package com.example.smart_waiting.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    EMAIL_ALREADY_EXIST("이미 존재하는 이메일 입니다.");
    private final String message;
}
