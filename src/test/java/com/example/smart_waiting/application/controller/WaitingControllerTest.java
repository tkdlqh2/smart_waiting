package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.GetWaitingsResultUsecase;
import com.example.smart_waiting.application.usecase.HandleWaitingUsecase;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WaitingController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JwtTokenProvider.class)
@MockBean(JpaMetamodelMappingContext.class)
class WaitingControllerTest {
    @MockBean
    private GetWaitingsResultUsecase getWaitingsResultUsecase;
    @MockBean
    private HandleWaitingUsecase handleWaitingUsecase;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerWaitingsSuccess() throws Exception {

        var authentication = getAuthToken(1L);

        given(getWaitingsResultUsecase.registerWaiting(1L,2L))
                .willReturn(new WaitingsResult(5,30));

        mockMvc.perform(post("/api/v1/waiting/register/2").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priorTeams").value(5))
                .andExpect(jsonPath("$.expectedWaitingTime").value(30))
                .andDo(print());
    }

    @Test
    void getCurrentWaitingSuccess() throws Exception {

        var authentication = getAuthToken(1L);

        given(getWaitingsResultUsecase.getWaitings(1L))
                .willReturn(new WaitingsResult(5,30));

        mockMvc.perform(get("/api/v1/waiting/get").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priorTeams").value(5))
                .andExpect(jsonPath("$.expectedWaitingTime").value(30))
                .andDo(print());
    }

    @Test
    void handleWaitingSuccess() throws Exception {

        var authentication = getAuthToken(3L);

        given(handleWaitingUsecase.handleWaiting((User)authentication.getPrincipal()))
                .willReturn(Waitings.builder().userId(2L).marketId(4L).build());

        mockMvc.perform(post("/api/v1/waiting/handle").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(2))
                .andExpect(jsonPath("$.marketId").value(4))
                .andDo(print());
    }
    private UsernamePasswordAuthenticationToken getAuthToken(Long id) {
        User user = User.builder().id(id).build();
        return new UsernamePasswordAuthenticationToken(user, null);
    }
}