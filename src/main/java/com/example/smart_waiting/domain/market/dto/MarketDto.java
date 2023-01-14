package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.FoodType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MarketDto {

    private final String name;
    private final String rcate2;
    private final FoodType foodType;
}
