package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.dto.MarketUpdateInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.market.type.FoodType;
import com.example.smart_waiting.domain.market.type.ParkType;
import com.example.smart_waiting.domain.market.type.WeekDay;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.exception.NoErrorException;
import com.example.smart_waiting.factory.MarketsFixtureFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.ALREADY_HAVE_MARKET;
import static com.example.smart_waiting.exception.error_code.MarketErrorCode.MARKET_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MarketWriteServiceTest {

    @Mock
    private MarketRepository marketRepository;

    @InjectMocks
    private MarketWriteService marketWriteService;

    @Test
    void registerMarketSuccess(){
        //given

        User ownerUser = User.builder()
                .id(1L)
                .build();

        MarketInput marketInput = MarketInput.builder()
                .name("행복한 식당1")
                .registrationNum("12345344")
                .rcate1("경기도 안산시")
                .rcate2("단원구")
                .detailAddress("xxx 건물 xxx 층 xx호")
                .openHour(7L)
                .closeHour(14L)
                .dayOffs(Set.of(WeekDay.SUNDAY,WeekDay.TUESDAY))
                .foodType(FoodType.CAFE)
                .parkType(ParkType.FORBIDDEN)
                .build();

        ArgumentCaptor<Market> captor = ArgumentCaptor.forClass(Market.class);
        given(marketRepository.existsByOwner(ownerUser)).willReturn(false);

        //when
        marketWriteService.register(ownerUser,marketInput);

        //then
        verify(marketRepository,times(1)).save(captor.capture());
        var capturedMarket = captor.getValue();
        assertEquals(1L,capturedMarket.getOwner().getId());
        assertEquals("행복한 식당1",capturedMarket.getName());
        assertEquals("12345344",capturedMarket.getRegistrationNum());
        assertEquals("경기도 안산시",capturedMarket.getRcate1());
        assertEquals("단원구",capturedMarket.getRcate2());
        assertEquals("xxx 건물 xxx 층 xx호",capturedMarket.getDetailAddress());
        assertEquals(7L,capturedMarket.getOpenHour());
        assertEquals(14L,capturedMarket.getCloseHour());
        assertTrue(capturedMarket.getDayOffs().contains(WeekDay.SUNDAY));
        assertTrue(capturedMarket.getDayOffs().contains(WeekDay.TUESDAY));
        assertEquals(2,capturedMarket.getDayOffs().size());
        assertEquals(ParkType.FORBIDDEN,capturedMarket.getParkType());
        assertEquals(FoodType.CAFE,capturedMarket.getFoodType());
    }

    @Test
    void registerMarketFail_alreadyHaveMarketUser(){
        //given
        User ownerUser = User.builder()
                .id(1L)
                .build();

        MarketInput marketInput = MarketInput.builder()
                .build();

        given(marketRepository.existsByOwner(ownerUser)).willReturn(true);
        //when
        //then
        try{
            marketWriteService.register(ownerUser,marketInput);
            throw new NoErrorException();
        }catch (Exception e){
            assertEquals(ALREADY_HAVE_MARKET.getMessage(),e.getMessage());
        }
    }

    @Test
    void updateMarketSuccess(){
        //given

        User ownerUser = User.builder()
                .id(1L)
                .build();

        MarketUpdateInput marketInput = MarketUpdateInput.builder()
                .openHour(7L)
                .closeHour(14L)
                .parkType(ParkType.VALET)
                .dayOffs(Set.of(WeekDay.SUNDAY,WeekDay.TUESDAY))
                .build();

        ArgumentCaptor<Market> captor = ArgumentCaptor.forClass(Market.class);
        given(marketRepository.findByOwner(ownerUser)).willReturn(Optional.of(MarketsFixtureFactory.create()));

        //when
        marketWriteService.updateMarket(ownerUser,marketInput);

        //then
        verify(marketRepository,times(1)).save(captor.capture());
        var capturedMarket = captor.getValue();
        assertEquals(7L,capturedMarket.getOpenHour());
        assertEquals(14L,capturedMarket.getCloseHour());
        assertEquals(ParkType.VALET,capturedMarket.getParkType());
        assertTrue(capturedMarket.getDayOffs().contains(WeekDay.SUNDAY));
        assertTrue(capturedMarket.getDayOffs().contains(WeekDay.TUESDAY));
        assertEquals(2,capturedMarket.getDayOffs().size());
    }

    @Test
    void updateMarketFail(){
        //given

        User ownerUser = User.builder()
                .id(1L)
                .build();

        MarketUpdateInput marketInput = MarketUpdateInput.builder()
                .openHour(7L)
                .closeHour(14L)
                .parkType(ParkType.VALET)
                .dayOffs(Set.of(WeekDay.SUNDAY,WeekDay.TUESDAY))
                .build();

        given(marketRepository.findByOwner(ownerUser)).willReturn(Optional.empty());

        //when
        //then
        try{
            marketWriteService.updateMarket(ownerUser,marketInput);
        } catch (Exception e){
            assertEquals(MARKET_NOT_FOUND.getMessage(), e.getMessage());
        }
    }

//    @Test
//    void deleteMarketSuccess(){
//        //given
//
//        User ownerUser = User.builder()
//                .id(1L)
//                .build();
//
//        MarketInput marketInput = MarketInput.builder()
//                .name("행복한 식당1")
//                .registrationNum("12345344")
//                .rcate1("경기도 안산시")
//                .rcate2("단원구")
//                .detailAddress("xxx 건물 xxx 층 xx호")
//                .openHour(7L)
//                .closeHour(14L)
//                .build();
//
//        ArgumentCaptor<Market> captor = ArgumentCaptor.forClass(Market.class);
//        given(marketRepository.existsByOwner(ownerUser)).willReturn(false);
//
//        //when
//        marketWriteService.register(ownerUser,marketInput);
//
//        //then
//        verify(marketRepository,times(1)).save(captor.capture());
//        var capturedMarket = captor.getValue();
//        assertEquals(1L,capturedMarket.getOwner().getId());
//        assertEquals("행복한 식당1",capturedMarket.getName());
//        assertEquals("12345344",capturedMarket.getRegistrationNum());
//        assertEquals("경기도 안산시",capturedMarket.getRcate1());
//        assertEquals("단원구",capturedMarket.getRcate2());
//        assertEquals("xxx 건물 xxx 층 xx호",capturedMarket.getDetailAddress());
//        assertEquals(7L,capturedMarket.getOpenHour());
//        assertEquals(14L,capturedMarket.getCloseHour());
//    }

}