package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.FoodType;
import lombok.Getter;

@Getter
public class MarketDto {

    private final Long id;
    private final String name;
    private final String rcate2;
    private final String foodType;

    public MarketDto(Long id, String name, String rcate2, FoodType foodType){
        this.id = id;
        this.name = name;
        this.rcate2 = rcate2;
        this.foodType = foodType.getMsg();
    }
}
