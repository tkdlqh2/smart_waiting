package com.example.smart_waiting.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalErrorResult> handleException(Exception e){
        log.error("Exception is occurred.", e);

        GlobalErrorResult result = GlobalErrorResult.of(e.getMessage());
        return new ResponseEntity<>(result, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<GlobalErrorResult> userExceptionHandler(UserException e){
        GlobalErrorResult result = GlobalErrorResult.of(e.getErrorCode().getMessage());
        return new ResponseEntity<>(result,e.getErrorCode().getHttpStatus());
    }

}
