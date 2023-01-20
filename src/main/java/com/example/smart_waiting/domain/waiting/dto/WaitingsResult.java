package com.example.smart_waiting.domain.waiting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingsResult {

    private int priorTeams;
    private int expectedWaitingTime;
}
