package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.RegisterMarketUserUsecase;
import com.example.smart_waiting.domain.market.dto.*;
import com.example.smart_waiting.domain.market.service.MarketReadService;
import com.example.smart_waiting.domain.market.service.MarketWriteService;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/market")
public class MarketController {

    private final MarketWriteService marketWriteService;
    private final MarketReadService marketReadService;
    private final RegisterMarketUserUsecase registerMarketUserUsecase;

    @PostMapping("/register")
    public ResponseEntity<String> register(Principal principal, @Valid @RequestBody MarketInput input){
        var user = (User) principal;
        registerMarketUserUsecase.register(user, input);
        return ResponseEntity.ok("음식점 등록 신청이 완료되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<PageCursor<MarketDto>> getMarketsByFilter(@RequestBody MarketFilter filter,
                                @RequestParam(required = false) Long key, @RequestParam int size){
        CursorRequest request = new CursorRequest(key,size);
        return ResponseEntity.ok(marketReadService.getMarketsByFilter(filter,request));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<MarketDetails> getMarketDetails(@PathVariable Long id){
        return ResponseEntity.ok(marketReadService.getMarketDetails(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<String> updateMarket(Principal principal, @RequestBody MarketUpdateInput input){
        var user = (User) principal;
        marketWriteService.updateMarket(user, input);
        return ResponseEntity.ok("음식점 변경이 완료되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMarket(Principal principal){
        var user = (User) principal;
        marketWriteService.deleteMarket(user);
        return ResponseEntity.ok("음식점 폐업이 완료되었습니다.");
    }
}
