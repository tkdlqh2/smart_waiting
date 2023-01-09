package com.example.smart_waiting.exception.error_code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode {

    EMAIL_ALREADY_EXIST("이미 존재하는 이메일 입니다.",HttpStatus.BAD_REQUEST),
    PHONE_ALREADY_EXIST("이미 존재하는 핸드폰 번호입니다.",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다.",HttpStatus.NOT_FOUND),
    CODE_ALREADY_EXPIRED("인증 만료일이 지났습니다. 재가입을 해주세요",HttpStatus.BAD_REQUEST),
    CODE_MISMATCH("코드가 일치하지 않습니다.",HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH("비밀번호가 일치하지 않습니다.",HttpStatus.BAD_REQUEST);
    private final String message;
    private final HttpStatus httpStatus;
}
