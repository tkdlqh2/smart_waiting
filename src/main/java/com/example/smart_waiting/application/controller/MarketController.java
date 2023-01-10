package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.service.MarketWriteService;
import com.example.smart_waiting.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/market")
public class MarketController {

    private final MarketWriteService marketWriteService;

    @PostMapping("/register")
    public ResponseEntity<String> register(Principal principal, @RequestBody MarketInput input){
        var user = (User) principal;
        marketWriteService.register(user, input);
        return ResponseEntity.ok("음식점 등록이 완료되었습니다.");
    }
}
