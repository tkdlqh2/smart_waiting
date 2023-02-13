package com.example.smart_waiting.domain.market.dto;


import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.type.WeekDay;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class MarketDetails {

    private final String name;
    private final String rcate1;
    private final String rcate2;
    private final String detailAddress;
    private final Long openHour;
    private final Long closeHour;
    private final String dayOffs;
    private final String foodType;
    private final String parkType;

    public static MarketDetails of(Market market){
        return MarketDetails.builder()
                .name(market.getName())
                .rcate1(market.getRcate1())
                .rcate2(market.getRcate2())
                .detailAddress(market.getDetailAddress())
                .openHour(market.getOpenHour())
                .closeHour(market.getCloseHour())
                .dayOffs(market.getDayOffs().stream().map(WeekDay::getMsg).reduce((x, y) -> x+","+y).orElse(""))
                .foodType(market.getFoodType().getMsg())
                .parkType(market.getParkType().getMsg()).build();
    }
}
