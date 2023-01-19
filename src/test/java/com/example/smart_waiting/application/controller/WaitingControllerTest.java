package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.application.usecase.RegisterWaitingsUsecase;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WaitingController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JwtTokenProvider.class)
@MockBean(JpaMetamodelMappingContext.class)
@WithMockUser
class WaitingControllerTest {
    @MockBean
    private RegisterWaitingsUsecase registerWaitingsUsecase;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerWaitingsSuccess() throws Exception {

        User user = User.builder().id(1L).build();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user, null);

        given(registerWaitingsUsecase.registerWaiting(any(),eq(2L)))
                .willReturn(new WaitingsResult(5,30));

        mockMvc.perform(post("/api/v1/waiting/register/2").principal(authentication))
                .andExpect(status().isOk())
                .andDo(print());
    }
}