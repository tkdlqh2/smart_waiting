package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketWaitingInfoDTO;
import com.example.smart_waiting.domain.market.repository.MarketWaitingTeamsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MarketReadService {

    private final MarketWaitingTeamsRepository marketWaitingTeamsRepository;


    public MarketWaitingInfoDTO getWaitingInfo(Long marketId) {

        Long waitingTeamsNum = marketWaitingTeamsRepository.getWaitingTeamsNum(marketId);
        Long totalExpectedTime = marketWaitingTeamsRepository.getExpectedTimePerTeam(marketId)
                *waitingTeamsNum;

        return MarketWaitingInfoDTO.builder()
                .waitingTeams(waitingTeamsNum).
                expectedMinutes(totalExpectedTime).
                build();
    }

}
