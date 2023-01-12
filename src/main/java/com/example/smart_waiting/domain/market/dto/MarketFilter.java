package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.FoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketFilter {
    private String rcate1;
    private String rcate2;
    private FoodType foodType;
    private boolean noParkingLotOk;

}
