package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.util.CursorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MarketReadServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private MarketWriteService marketReadService;

    @Test
    void getMarketsByFilterSuccess(){
        //given
        MarketFilter marketFliter = MarketFilter.builder()
                                    .rcate1()
                                    .rcate2()
                                    .foodType()
                                    .isParkAble()
                                    .build();

        CursorRequest request= new CursorRequest(10L,10);

        ArgumentCaptor<Market> captor = ArgumentCaptor.forClass(Market.class);
        given(marketRepository.getMarkets(marketFliter, request)).willReturn();

        //when
        marketWriteService.getMarketsByFilter(marketFliter);

        //then
        verify(marketRepository,times(1)).save(captor.capture());
        var capturedMarket = captor.getValue();
    }
}
