package com.example.smart_waiting.domain.user.service;

import com.example.smart_waiting.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserReadServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserReadService userReadService;

    @Test
    void existEmailSuccess(){
        //given
        given(userRepository.existsByEmail(anyString())).willReturn(false);
        //when
        var result = userReadService.existEmail("abc@gmail.com");
        //then
        assertTrue(result);
    }
}