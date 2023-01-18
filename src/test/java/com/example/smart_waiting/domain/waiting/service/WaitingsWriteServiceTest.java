package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.repository.WaitingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WaitingsWriteServiceTest {

    @Mock
    WaitingsRepository waitingsRepository;
    @InjectMocks
    WaitingsWriteService waitingsWriteService;

    @Test
    void registerWaitingsSuccess(){
        //given
        User user = User.builder().id(2L).build();
        ArgumentCaptor<Waitings> captor = ArgumentCaptor.forClass(Waitings.class);
        given(waitingsRepository.existsByUserId(user.getId())).willReturn(false);
        //when
        waitingsWriteService.registerWaiting(user, 10L);

        //then
        verify(waitingsRepository,times(1)).save(captor.capture());
        var capturedWaitings = captor.getValue();
        assertEquals(user.getId(),capturedWaitings.getUserId());
        assertEquals(10L,capturedWaitings.getMarketId());
    }
}