package com.example.smart_waiting.application.usecase;

import com.example.smart_waiting.domain.market.service.MarketReadService;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.service.WaitingsReadService;
import com.example.smart_waiting.domain.waiting.service.WaitingsWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetWaitingsResultUsecase {
    private final WaitingsWriteService waitingsWriteService;
    private final WaitingsReadService waitingsReadService;
    private final MarketReadService marketReadService;

    @Transactional
    public WaitingsResult registerWaiting(Long userId, Long marketId){
        var priorTeams = waitingsWriteService.registerWaiting(userId, marketId);
        return marketReadService.getWaitingsResult(marketId,priorTeams);
    }

    @Transactional
    public WaitingsResult getWaitings(Long userId){
        var result = waitingsReadService.getCurrentWaiting(userId);
        return marketReadService.getWaitingsResult(result.getMarketId(), result.getPriorTeams());
    }

    public Waitings handleWaiting(Long userId) {
        return null;
    }
}
