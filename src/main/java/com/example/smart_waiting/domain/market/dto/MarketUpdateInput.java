package com.example.smart_waiting.domain.market.dto;

import com.example.smart_waiting.domain.market.type.ParkType;
import com.example.smart_waiting.domain.market.type.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MarketUpdateInput {

    private Long openHour;
    private Long closeHour;
    private Set<WeekDay> dayOffs;
    private ParkType parkType;
}
