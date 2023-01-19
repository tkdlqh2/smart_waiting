package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.repository.WaitingsRepository;
import com.example.smart_waiting.exception.NoErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.smart_waiting.exception.error_code.WaitingsErrorCode.ALREADY_REGISTERED_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        given(waitingsRepository.countByMarketId(10L)).willReturn(5);
        //when
        int result = waitingsWriteService.registerWaiting(user, 10L);

        //then
        verify(waitingsRepository,times(1)).save(captor.capture());
        var capturedWaitings = captor.getValue();
        assertEquals(user.getId(),capturedWaitings.getUserId());
        assertEquals(10L,capturedWaitings.getMarketId());
        assertEquals(5,result);

    }

    @Test
    void registerWaitingsFail_alreadyRegisteredUser(){
        //given
        User user = User.builder().id(2L).build();
        given(waitingsRepository.existsByUserId(user.getId())).willReturn(true);
        //when
        //then
        try {
            waitingsWriteService.registerWaiting(user, 10L);
            throw new NoErrorException();
        } catch (Exception e){
            assertEquals(ALREADY_REGISTERED_USER.getMessage(),e.getMessage());
        }
    }
}