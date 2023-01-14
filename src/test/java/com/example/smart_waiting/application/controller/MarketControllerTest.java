package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.RegisterMarketUserUsecase;
import com.example.smart_waiting.domain.market.dto.*;
import com.example.smart_waiting.domain.market.service.MarketReadService;
import com.example.smart_waiting.domain.market.service.MarketWriteService;
import com.example.smart_waiting.domain.market.type.ParkType;
import com.example.smart_waiting.domain.market.type.WeekDay;
import com.example.smart_waiting.exception.exception_class.MarketException;
import com.example.smart_waiting.factory.MarketsFixtureFactory;
import com.example.smart_waiting.security.JwtTokenProvider;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MarketController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(JwtTokenProvider.class)
class MarketControllerTest {

    @MockBean
    private MarketWriteService marketWriteService;
    @MockBean
    private MarketReadService marketReadService;
    @MockBean
    private RegisterMarketUserUsecase registerMarketUserUsecase;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerSuccess() throws Exception {

        doNothing().when(registerMarketUserUsecase).register(any(),any());

        mockMvc.perform(post("/api/v1/market/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MarketInput.builder()
                                        .name("맛있는 음식점 1")
                                        .registrationNum("000-11-22222")
                                        .rcate1("경기도 안산시")
                                        .rcate2("단원구")
                                        .detailAddress("상세주소입니다")
                                        .openHour(1L)
                                        .closeHour(14L)
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("음식점 등록이 완료되었습니다."))
                .andDo(print());
    }

    @Test
    void registerFail_alreadyMarketHaveUser() throws Exception {

        doThrow(new MarketException(ALREADY_HAVE_MARKET)).when(registerMarketUserUsecase).register(any(),any());

        mockMvc.perform(post("/api/v1/market/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MarketInput.builder()
                                        .name("맛있는 음식점 1")
                                        .registrationNum("000-11-22222")
                                        .rcate1("경기도 안산시")
                                        .rcate2("단원구")
                                        .detailAddress("상세주소입니다")
                                        .openHour(1L)
                                        .closeHour(14L)
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(ALREADY_HAVE_MARKET.getMessage()))
                .andDo(print());
    }
    @Test
    void registerFail_invalidInput() throws Exception {

        doNothing().when(registerMarketUserUsecase).register(any(),any());

        mockMvc.perform(post("/api/v1/market/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MarketInput.builder()
                                        .name("맛있는 음식점 1")
                                        .registrationNum("")
                                        .rcate1("경기도 안산시")
                                        .rcate2("단원구")
                                        .detailAddress("상세주소입니다")
                                        .openHour(1L)
                                        .closeHour(25L)
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getMarketsByFilterSuccess() throws Exception {
        //given

        CursorRequest request = new CursorRequest(10L,20);
        List<MarketDto> body = MarketsFixtureFactory.createLists(20).stream()
                .map(x-> new MarketDto(x.getId(),x.getName(),x.getRcate2(),x.getFoodType()))
                .collect(Collectors.toList());

        given(marketReadService.getMarketsByFilter(any(),any()))
                .willReturn(new PageCursor<>(request.next(10L),body));


        //when
        //then
        mockMvc.perform(get("/api/v1/market/list?size=10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                        MarketFilter.builder()
                                .rcate2s(Collections.emptyList())
                                .foodTypes(Collections.emptyList())
                                .noParkingLotOk(false)
                                .build())))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void getMarketDetailsSuccess() throws Exception {
        //given
        MarketDetails marketDetails = MarketDetails.of(MarketsFixtureFactory.create());
        given(marketReadService.getMarketDetails(1L))
                .willReturn(marketDetails);
        //when
        //then
        mockMvc.perform(get("/api/v1/market/details/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(marketDetails.getName()))
                .andExpect(jsonPath("$.rcate1").value(marketDetails.getRcate1()))
                .andExpect(jsonPath("$.rcate2").value(marketDetails.getRcate2()))
                .andExpect(jsonPath("$.detailAddress").value(marketDetails.getDetailAddress()))
                .andExpect(jsonPath("$.openHour").value(marketDetails.getOpenHour()))
                .andExpect(jsonPath("$.closeHour").value(marketDetails.getCloseHour()))
                .andExpect(jsonPath("$.dayOffs").value(marketDetails.getDayOffs()))
                .andExpect(jsonPath("$.foodType").value(marketDetails.getFoodType()))
                .andExpect(jsonPath("$.parkType").value(marketDetails.getParkType()))
                .andDo(print());
    }

    @Test
    void updateMarketSuccess() throws Exception{
        //given
        doNothing().when(marketWriteService).updateMarket(any(),any());
        //when
        //then
        mockMvc.perform(patch("/api/v1/market/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(
                        MarketUpdateInput.builder()
                                .openHour(1L)
                                .closeHour(14L)
                                .dayOffs(Set.of(WeekDay.SUNDAY))
                                .parkType(ParkType.FORBIDDEN)
                                .build()
                )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("음식점 변경이 완료되었습니다."))
                .andDo(print());
    }

    @Test
    void updateMarketFail_NoMarket() throws Exception{
        //given
        doThrow(new MarketException(MARKET_NOT_FOUND)).when(marketWriteService).updateMarket(any(),any());
        //when
        //then
        mockMvc.perform(patch("/api/v1/market/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MarketUpdateInput.builder()
                                        .openHour(1L)
                                        .closeHour(14L)
                                        .dayOffs(Set.of(WeekDay.SUNDAY))
                                        .parkType(ParkType.FORBIDDEN)
                                        .build()
                        )))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(MARKET_NOT_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    void updateMarketFail_NotApproved() throws Exception{
        //given
        doThrow(new MarketException(MARKET_STATUS_IS_NOT_APPROVED)).when(marketWriteService).updateMarket(any(),any());
        //when
        //then
        mockMvc.perform(patch("/api/v1/market/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                MarketUpdateInput.builder()
                                        .openHour(1L)
                                        .closeHour(14L)
                                        .dayOffs(Set.of(WeekDay.SUNDAY))
                                        .parkType(ParkType.FORBIDDEN)
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(MARKET_STATUS_IS_NOT_APPROVED.getMessage()))
                .andDo(print());
    }
}