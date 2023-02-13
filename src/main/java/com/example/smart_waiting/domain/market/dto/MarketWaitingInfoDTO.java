package com.example.smart_waiting.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketWaitingInfoDTO {

    private Long waitingTeams;
    private Long expectedMinutes;

}
