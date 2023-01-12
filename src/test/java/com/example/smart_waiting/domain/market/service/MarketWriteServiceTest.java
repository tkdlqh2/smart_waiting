package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.exception.NoErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.ALREADY_HAVE_MARKET;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
                .zipCode(15484L)
                .detailAddress("xxx 건물 xxx 층 xx호")
                .openHour(7L)
                .closeHour(14L)
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
        assertEquals(15484L,capturedMarket.getZipCode());
        assertEquals("xxx 건물 xxx 층 xx호",capturedMarket.getDetailAddress());
        assertEquals(7L,capturedMarket.getOpenHour());
        assertEquals(14L,capturedMarket.getCloseHour());
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

}