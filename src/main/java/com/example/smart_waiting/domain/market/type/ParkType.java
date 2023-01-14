package com.example.smart_waiting.domain.market.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParkType {
    VALET("발렛 주차가 가능합니다"),
    HAS_PARKING_LOT("주차장이 있습니다"),
    FORBIDDEN("별도의 주차장이 없습니다.");

    private final String msg;
}
