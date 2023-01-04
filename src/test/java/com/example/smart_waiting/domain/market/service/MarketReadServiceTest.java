package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketWaitingInfoDTO;
import com.example.smart_waiting.domain.market.repository.MarketWaitingTeamsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MarketReadServiceTest {

    @Mock
    private MarketWaitingTeamsRepository marketWaitingTeamsRepository;

    @InjectMocks
    private MarketReadService marketReadService;

    private final Long MARKET_ID = 1L;

    @Test
    void getWaitingInfoSuccess(){
        //given
        given(marketWaitingTeamsRepository.getWaitingTeamsNum(MARKET_ID)).willReturn(2L);
        given(marketWaitingTeamsRepository.getExpectedTimePerTeam(MARKET_ID)).willReturn(5L);

        //when
        MarketWaitingInfoDTO result = marketReadService.getWaitingInfo(MARKET_ID);

        //then
        assertEquals(2L, result.getWaitingTeams());
        assertEquals(10L,result.getExpectedMinutes());

    }


}
