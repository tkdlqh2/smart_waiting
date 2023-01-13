package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.FoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketFilter {
    private List<String> rcate2s;
    private List<FoodType> foodTypes;
    private boolean noParkingLotOk;

}
