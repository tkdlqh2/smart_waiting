package com.example.smart_waiting.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketInput {
    private String name;
    private String registrationNum;
    private Long zipCode;
    private String detailAddress;
    private Long openHour;
    private Long closeHour;
}
