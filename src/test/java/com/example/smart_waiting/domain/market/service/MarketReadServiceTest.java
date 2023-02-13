package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketWaitingInfoDTO;
import com.example.smart_waiting.domain.market.repository.MarketWaitingTeamsRepository;
import com.example.smart_waiting.domain.market.dto.MarketDetails;
import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.exception.NoErrorException;
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

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.MARKET_NOT_FOUND;
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


    private MarketRepository marketRepository;

    @InjectMocks
    private MarketReadService marketReadService;
    private static final Long DEFAULT_WAITING_TIME_PER_TEAM = 5L;

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

    @Test
    void getMarketDetailsSuccess(){
        //given
        Market createdMarket = MarketsFixtureFactory.create();
        given(marketRepository.findById(1L)).willReturn(Optional.of(createdMarket));
        MarketDetails marketDetails = MarketDetails.of(createdMarket);
        //when
        MarketDetails result = marketReadService.getMarketDetails(1L);
        //then
        assertEquals(marketDetails.getName(), result.getName());
        assertEquals(marketDetails.getRcate1(), result.getRcate1());
        assertEquals(marketDetails.getRcate2(), result.getRcate2());
        assertEquals(marketDetails.getDetailAddress(), result.getDetailAddress());
        assertEquals(marketDetails.getOpenHour(), result.getOpenHour());
        assertEquals(marketDetails.getCloseHour(), result.getCloseHour());
        assertEquals(marketDetails.getDayOffs(), result.getDayOffs());
        assertEquals(marketDetails.getFoodType(), result.getFoodType());
        assertEquals(marketDetails.getParkType(), result.getParkType());
    }

    @Test
    void getMarketDetailsFail_noMarket(){
        //given
        given(marketRepository.findById(1L)).willReturn(Optional.empty());
        //when
        //then
        try {
            marketReadService.getMarketDetails(1L);
            throw new NoErrorException();
        }catch (Exception e){
               assertEquals(MARKET_NOT_FOUND.getMessage(),e.getMessage());
        }
    }

    @Test
    void getWaitingsResultSuccess(){
        //given
        Market market = Market.builder().id(1L).build();
        given(marketRepository.findById(1L)).willReturn(Optional.of(market));

        //when
        WaitingsResult result = marketReadService.getWaitingsResult(1L,5);
        //then
        assertEquals(5, result.getPriorTeams());
        assertEquals((5+1)*DEFAULT_WAITING_TIME_PER_TEAM, result.getExpectedWaitingTime());
    }

    @Test
    void getWaitingsResultFail_noMarket(){
        //given
        given(marketRepository.findById(1L)).willReturn(Optional.empty());
        //when
        //then
        try {
            marketReadService.getWaitingsResult(1L,5);
            throw new NoErrorException();
        }catch (Exception e){
            assertEquals(MARKET_NOT_FOUND.getMessage(),e.getMessage());
        }
    }

    @Test
    void getMarketByOwnerSuccess(){
        //given
        User owner = User.builder().id(1L).build();
        Market market = Market.builder().owner(owner).id(5L).build();
        given(marketRepository.findByOwner(owner)).willReturn(Optional.of(market));

        //when
        var result = marketReadService.getMarketByOwner(owner);
        //then
        assertEquals(5, result.getMarketId());
    }

    @Test
    void getMarketByOwnerFail_NoMarket(){
        //given
        User owner = User.builder().id(1L).build();
        given(marketRepository.findByOwner(owner)).willReturn(Optional.empty());

        //when
        //then
        try {
            marketReadService.getMarketByOwner(owner);
            throw new NoErrorException();
        }catch (Exception e){
            assertEquals(MARKET_NOT_FOUND.getMessage(),e.getMessage());
        }
    }
}
