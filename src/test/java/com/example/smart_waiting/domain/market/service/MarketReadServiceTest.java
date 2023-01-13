package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.factory.MarketsFixtureFactory;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MarketReadServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private MarketReadService marketReadService;

    @Test
    void getMarketsByFilterSuccess(){
        //given
        MarketFilter marketFilter = MarketFilter.builder()
                                    .rcate2s(Collections.singletonList("강남구"))
                                    .foodTypes(new ArrayList<>())
                                    .noParkingLotOk(true)
                                    .build();

        CursorRequest request= new CursorRequest(null,20);
        List<Market> body = MarketsFixtureFactory.createLists(20);

        given(marketRepository.findByFilter(marketFilter, request)).willReturn(
                new PageCursor<>(request.next(10L),body));

        //when
        var result = marketReadService.getMarketsByFilter(marketFilter,request);

        //then
        assertEquals(body,result.getBody());
        assertEquals(10L,result.getNextCursorRequest().getKey());
        assertEquals(20L,result.getNextCursorRequest().getSize());
    }

}
