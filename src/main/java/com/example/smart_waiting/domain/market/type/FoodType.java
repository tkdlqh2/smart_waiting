package com.example.smart_waiting.domain.market.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FoodType {
    KOREANS("한식"), JAPANS("일식"), CHINESE("중식"), WESTERN("양식"),
    OTHERS("세계 음식"), BUFFET("뷔페"), CAFE("카페"), PUB("주점");
    private final String msg;
}
