package com.example.smart_waiting.domain.waiting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CurrentWaitingResult {

    private final Long marketId;
    private final int priorTeams;
}
