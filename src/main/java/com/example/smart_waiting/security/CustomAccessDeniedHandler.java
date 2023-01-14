package com.example.smart_waiting.security;

import com.example.smart_waiting.exception.exception_class.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.smart_waiting.exception.error_code.SecurityErrorCode.ACCESS_DENIED;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        log.info("[handle] 접근이 막혔을 경우 exception 발생");
        throw new SecurityException(ACCESS_DENIED);
    }
}
