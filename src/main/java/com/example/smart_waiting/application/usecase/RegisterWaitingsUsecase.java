package com.example.smart_waiting.application.usecase;

import com.example.smart_waiting.domain.market.service.MarketReadService;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.domain.waiting.service.WaitingsWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterWaitingsUsecase {
    private final WaitingsWriteService waitingsWriteService;
    private final MarketReadService marketReadService;

    public WaitingsResult registerWaiting(Long userId, Long marketId){
        var priorTeams = waitingsWriteService.registerWaiting(userId, marketId);
        return marketReadService.getWaitingsResult(marketId,priorTeams);
    }
}
