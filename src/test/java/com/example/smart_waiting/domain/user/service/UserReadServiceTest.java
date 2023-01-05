package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserReadServiceTest {
    @Mock
    private PasswordEncoder passwordEncoder;
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
    void logInSuccess(){
        //given
        User user = User.builder()
                .email("abc@gmail.com")
                .password("2222")
                .build();

        given(userRepository.findByEmail("abc@gmail.com")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("1111","2222")).willReturn(true);
//        given(TokenUtil.generateToken()).willReturn("Token string");
        //when
        String result = userReadService.logIn("abc@gmail.com","1111");
        //then
        assertEqual("Token string",result);
    }
}