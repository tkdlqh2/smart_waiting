package com.example.smart_waiting.application.usecase;

import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.service.MarketReadService;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.service.WaitingsWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandleWaitingUsecase {
    private final MarketReadService marketReadService;
    private final WaitingsWriteService waitingsWriteService;


    public Waitings handleWaiting(Long ownerId) {
        Market market  = marketReadService.getMarketByOwnerId(ownerId);
        return waitingsWriteService.handleWaitings(market.getId());
    }
}
