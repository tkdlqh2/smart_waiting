package com.example.smart_waiting.application.controller;

import com.example.smart_waiting.domain.user.dto.UserInput;
import com.example.smart_waiting.domain.user.dto.UserLogInInput;
import com.example.smart_waiting.domain.user.service.UserReadService;
import com.example.smart_waiting.domain.user.service.UserWriteService;
import com.example.smart_waiting.exception.exception_class.UserException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.smart_waiting.exception.error_code.UserErrorCode.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    private UserReadService userReadService;
    @MockBean
    private UserWriteService userWriteService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void registerSuccess() throws Exception {

        doNothing().when(userWriteService).createUser(
                any());

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .name("홍길동")
                                        .phone("010-1111-2222")
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void registerFail_emailAlreadyExist() throws Exception {

        doThrow(new UserException(EMAIL_ALREADY_EXIST))
                .when(userWriteService).createUser(any());

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .name("홍길동")
                                        .phone("010-1111-2222")
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(EMAIL_ALREADY_EXIST.getMessage()))
                .andDo(print());
    }

    @Test
    void registerFail_phoneAlreadyExist() throws Exception {

        doThrow(new UserException(PHONE_ALREADY_EXIST))
                .when(userWriteService).createUser(any());

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .name("홍길동")
                                        .phone("010-1111-2222")
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(PHONE_ALREADY_EXIST.getMessage()))
                .andDo(print());
    }


    @Test
    void registerFail_invalidInput() throws Exception {

        doNothing().when(userWriteService).createUser(
                any());

        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserInput.builder()
                                        .email("abc")
                                        .password("1111")
                                        .name("df")
                                        .phone("asd")
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void existEmailSuccess() throws Exception {

        given(userReadService.existEmail(any()))
                .willReturn(true);

        mockMvc.perform(get("/api/v1/user/email/abc@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andDo(print());
    }

    @Test
    void existEmailFail_invalidInput() throws Exception {

        given(userReadService.existEmail(any()))
                .willReturn(true);

        mockMvc.perform(get("/api/v1/user/email/1111"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void existPhoneSuccess() throws Exception {

        given(userReadService.existPhone(any()))
                .willReturn(true);

        mockMvc.perform(get("/api/v1/user/phone/010-1111-2222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andDo(print());
    }

    @Test
    void emailAuthSuccess() throws Exception {

        doNothing().when(userWriteService).emailAuth(1L,"authKey");

        mockMvc.perform(get("/api/v1/user/1/auth/authKey"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void emailAuthFail_userNotFound() throws Exception {

        doThrow(new UserException(USER_NOT_FOUND)).when(userWriteService).emailAuth(1L,"authKey");

        mockMvc.perform(get("/api/v1/user/1/auth/authKey"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(USER_NOT_FOUND.getMessage()))
                .andDo(print());
    }
    @Test
    void emailAuthFail_codeAlreadyExpired() throws Exception {

        doThrow(new UserException(CODE_ALREADY_EXPIRED)).when(userWriteService).emailAuth(1L,"authKey");

        mockMvc.perform(get("/api/v1/user/1/auth/authKey"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(CODE_ALREADY_EXPIRED.getMessage()))
                .andDo(print());
    }

    @Test
    void emailAuthFail_codeMismatch() throws Exception {

        doThrow(new UserException(CODE_MISMATCH)).when(userWriteService).emailAuth(1L,"authKey");

        mockMvc.perform(get("/api/v1/user/1/auth/authKey"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(CODE_MISMATCH.getMessage()))
                .andDo(print());
    }

    @Test
    void signInSuccess() throws Exception {

        given(userReadService.signIn(
                any())).willReturn("token");

        mockMvc.perform(get("/api/v1/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserLogInInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .build()
                        )))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("token"))
                .andDo(print());
    }

    @Test
    void signInFail_NoUser() throws Exception {

        doThrow(new UserException(USER_NOT_FOUND)).when(userReadService).signIn(any());

        mockMvc.perform(get("/api/v1/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserLogInInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .build()
                        )))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value(USER_NOT_FOUND.getMessage()))
                .andDo(print());
    }

    @Test
    void signInFail_passwordNotMatch() throws Exception {

        doThrow(new UserException(PASSWORD_NOT_MATCH)).when(userReadService).signIn(any());

        mockMvc.perform(get("/api/v1/user/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                UserLogInInput.builder()
                                        .email("abc@gmail.com")
                                        .password("Qlalfqjsgh!1")
                                        .build()
                        )))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value(PASSWORD_NOT_MATCH.getMessage()))
                .andDo(print());
    }
}