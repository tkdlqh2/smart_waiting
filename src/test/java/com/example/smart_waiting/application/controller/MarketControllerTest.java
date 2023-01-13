package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.service.MarketWriteService;
import com.example.smart_waiting.exception.exception_class.MarketException;
import com.example.smart_waiting.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.ALREADY_HAVE_MARKET;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerSuccess() throws Exception {

        doNothing().when(marketWriteService).register(any(),any());

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

        doThrow(new MarketException(ALREADY_HAVE_MARKET)).when(marketWriteService).register(any(),any());

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

        doNothing().when(marketWriteService).register(any(),any());

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
}