package com.example.smart_waiting.domain.user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
void class UserWriteServiceTest {

    @InjectMocks
    private UserWriteService userWriteService;

    @Test
    void createUserSuccess(){
        //given
        UserInput userInput = UserInput.builder()
                .email("yhj7124@naver.com")
                .password("1111")
                .name("유형진")
                .phone("010-1111-2222")
                .build();

        given(passwordEncoder.encode("1111")).willReturn("2222");
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        //when
        userWriteService.createUser(userInput);

        //then
        verify(userRepository,times(1)).save(captor.capture());
    }
}
