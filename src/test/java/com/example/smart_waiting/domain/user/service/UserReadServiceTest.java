package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.dto.UserLogInInput;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import com.example.smart_waiting.exception.NoErrorException;
import com.example.smart_waiting.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static com.example.smart_waiting.exception.error_code.UserErrorCode.PASSWORD_NOT_MATCH;
import static com.example.smart_waiting.exception.error_code.UserErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserReadServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserReadService userReadService;

    @Test
    void existEmailSuccess(){
        //given
        given(userRepository.existsByEmail(anyString())).willReturn(true);
        //when
        var result = userReadService.existEmail("abc@gmail.com");
        //then
        assertTrue(result);
    }

    @Test
    void existPhoneSuccess(){
        //given
        given(userRepository.existsByPhone(anyString())).willReturn(true);
        //when
        var result = userReadService.existPhone("010-1111-2222");
        //then
        assertTrue(result);
    }

    @Test
    void signInSuccess() {

        //given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(User.builder()
                                .email("abc@gmail.com")
                                .password("2222")
                                .roles(Collections.singletonList("USER"))
                                .build()));

        given(passwordEncoder.matches("1111","2222"))
                .willReturn(true);

        given(jwtTokenProvider.createToken("abc@gmail.com",Collections.singletonList("USER")))
                        .willReturn("TOKEN_STRING");
        //when
        UserLogInInput parameter = UserLogInInput.builder()
                .email("abc@gmail.com")
                .password("1111")
                .build();

        var token = userReadService.signIn(parameter);
        //then
        assertEquals( "TOKEN_STRING",token);
    }

    @Test
    void signInFail_userNotFound() {
        //given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        //when
        //then
        UserLogInInput parameter = UserLogInInput.builder()
                .email("abc@gmail.com")
                .password("1111")
                .build();
        try{
            userReadService.signIn(parameter);
            throw new NoErrorException();
        }catch (Exception e){
            assertEquals(USER_NOT_FOUND.getMessage(),e.getMessage());
        }
    }

    @Test
    void signInFail_passwordNotMatch() {
        //given
        given(userRepository.findByEmail(anyString()))
                .willReturn(Optional.of(User.builder()
                        .email("abc@gmail.com")
                        .password("2222")
                        .roles(Collections.singletonList("USER"))
                        .build()));

        given(passwordEncoder.matches("1111","2222"))
                .willReturn(false);

        //when
        //then
        UserLogInInput parameter = UserLogInInput.builder()
                .email("abc@gmail.com")
                .password("1111")
                .build();
        try{
            userReadService.signIn(parameter);
            throw new NoErrorException();
        }catch (Exception e){
            assertEquals(PASSWORD_NOT_MATCH.getMessage(),e.getMessage());
        }
    }
}