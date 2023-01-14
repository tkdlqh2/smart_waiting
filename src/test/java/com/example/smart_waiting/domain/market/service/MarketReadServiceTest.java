package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketDetails;
import com.example.smart_waiting.domain.market.dto.MarketDto;
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
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<MarketDto> body = MarketsFixtureFactory.createLists(20).stream()
                .map(x-> new MarketDto(x.getId(),x.getName(),x.getRcate2(),x.getFoodType()))
                .collect(Collectors.toList());

        given(marketRepository.findByFilter(marketFilter, request)).willReturn(
                new PageCursor<>(request.next(10L),body));

        //when
        var result = marketReadService.getMarketsByFilter(marketFilter,request);

        //then
        assertEquals(body,result.getBody());
        assertEquals(10L,result.getNextCursorRequest().getKey());
        assertEquals(20L,result.getNextCursorRequest().getSize());
//        body.stream().forEach(x-> System.out.println(x.getRcate2()+" "+x.getId()+" "+x.getFoodType()+" "+x.getName()));
    }

    void getMarketDetailsSuccess(){
        //given
        Market createdMarket = MarketsFixtureFactory.create();
        given(marketRepository.findById(1L)).willReturn(Optional.of(createdMarket));
        //when
        MarketDetails result = marketReadService.getMarketDetail(1L);
        //then
        assertEquals(createdMarket.getName(), result.getName());
        assertEquals(createdMarket.getRcate1(), result.getRcate1());
        assertEquals(createdMarket.getRcate2(), result.getRcate2());
        assertEquals(createdMarket.getDetailAddress(), result.getDetailAddress());
        assertEquals(createdMarket.getOpenHour(), result.getOpenHour());
        assertEquals(createdMarket.getCloseHour(), result.getCloseHour());
        assertEquals(createdMarket.getDayOffs(), result.getDayOffs());
        assertEquals(createdMarket.getFoodType(), result.getFoodType());
        assertEquals(createdMarket.getParkType(), result.getParkType());;
    }

}
