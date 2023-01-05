package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.service.UserWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserWriteService userWriteService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid UserInput userInput){
        userWriteService.createUser(userInput);
        return ResponseEntity.ok("회원 가입 신청이 완료되었습니다.");
    }

}
