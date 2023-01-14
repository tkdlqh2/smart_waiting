package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.dto.MarketInput;
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

    @PostMapping("/register")
    public ResponseEntity<String> register(Principal principal, @Valid @RequestBody MarketInput input){
        var user = (User) principal;
        marketWriteService.register(user, input);
        return ResponseEntity.ok("음식점 등록이 완료되었습니다.");
    }

    @GetMapping("/list")
    public ResponseEntity<PageCursor<MarketDto>> getMarketsByFilter(@RequestBody MarketFilter filter,
                                                                    @RequestBody CursorRequest request){

        return ResponseEntity.ok(marketReadService.getMarketsByFilter(filter,request));
    }

}
