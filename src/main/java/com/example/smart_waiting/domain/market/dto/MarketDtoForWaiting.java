package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.entity.Market;
import lombok.Getter;

@Getter
public class MarketDtoForWaiting {

    private Long marketId;
    private Long expectedTimePerTeam;

    public MarketDtoForWaiting(Market market){
        this.marketId = market.getId();
        this.expectedTimePerTeam = market.getWaitingTimePerTeam();
    }
}
