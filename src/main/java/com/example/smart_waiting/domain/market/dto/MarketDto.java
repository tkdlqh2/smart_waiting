package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.FoodType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class MarketDto {

    private final Long id;
    private final String name;
    private final String rcate2;
    private final FoodType foodType;
}
