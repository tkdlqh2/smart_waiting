package com.example.smart_waiting.domain.market.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MarketReadServiceTest {

    @InjectMocks
    private MarketReadService marketReadService;

    private final Long MARKET_ID = 1L;

    @Test
    public void getWaitingInfoSuccess(){
        //given

        //when
        MarketWaitingInfoDTO result = marketReadService.getWaitingInfo(MARKET_ID);

        //then
        assertEquals(result.getWaitingTeams());
        assertEquals(result.getExpectedMinutes());

    }


}
