package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.RegisterWaitingsUsecase;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/waiting")
public class WaitingController {

    private final RegisterWaitingsUsecase registerWaitingsUsecase;

    @PostMapping("/register/{marketId}")
    public ResponseEntity<WaitingsResult> registerWaiting(Principal principal, @PathVariable Long marketId){
        User user = (User) principal;
        return ResponseEntity.ok(registerWaitingsUsecase.registerWaiting(user.getId(), marketId));
    }
}
