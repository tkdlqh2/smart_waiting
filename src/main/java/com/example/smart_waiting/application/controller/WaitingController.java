package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.GetWaitingsResultUsecase;
import com.example.smart_waiting.application.usecase.HandleWaitingUsecase;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/waiting")
public class WaitingController {

    private final GetWaitingsResultUsecase getWaitingsResultUsecase;
    private final HandleWaitingUsecase handleWaitingUsecase;

    @PostMapping("/register/{marketId}")
    public ResponseEntity<WaitingsResult> registerWaiting(Authentication authentication, @PathVariable Long marketId){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(getWaitingsResultUsecase.registerWaiting(user.getId(), marketId));
    }

    @GetMapping("/get")
    public ResponseEntity<WaitingsResult> getCurrentWaiting(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(getWaitingsResultUsecase.getWaitings(user.getId()));
    }

    @PostMapping("/handle")
//    @PreAuthorize("MARKET")
    public ResponseEntity<Waitings> handleWaiting(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(handleWaitingUsecase.handleWaiting(user));
    }
}
