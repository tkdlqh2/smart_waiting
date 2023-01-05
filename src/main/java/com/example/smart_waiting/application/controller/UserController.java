package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.service.UserReadService;
import com.example.smart_waiting.domain.user.service.UserWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserReadService userReadService;
    private final UserWriteService userWriteService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid UserInput userInput){
        userWriteService.createUser(userInput);
        return ResponseEntity.ok("회원 가입 신청이 완료되었습니다.");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> existEmail(@Valid @Email @PathVariable String email){
        return ResponseEntity.ok(userReadService.existEmail(email));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<Boolean> existPhone(@Valid @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
                                                  @PathVariable String phone){
        return ResponseEntity.ok(userReadService.existPhone(phone));
    }

}
