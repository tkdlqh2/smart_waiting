package com.example.smart_waiting.domain.market.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WeekDay {

    SUNDAY("일"),MONDAY("월"),TUESDAY("화"),WEDNESDAY("수"),
    THURSDAY("목"), FRIDAY("금"), SATURDAY("토");
     private final String msg;

}
