package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.repository.WaitingsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WaitingsReadServiceTest {

    @Mock
    private WaitingsRepository waitingsRepository;
    @InjectMocks
    private WaitingsReadService waitingsReadService;
    @Test
    void getCurrentWaiting() {
        //given
        given(waitingsRepository.findByUserId(1L))
                .willReturn(Optional.of(Waitings.builder()
                                                .userId(1L)
                                                .marketId(3L)
                                                .build()));

        given(waitingsRepository.findAllByMarketId(3L))
                .willReturn(List.of(
                                Waitings.builder().userId(2L).marketId(3L).build(),
                                Waitings.builder().userId(3L).marketId(3L).build(),
                                Waitings.builder().userId(4L).marketId(3L).build(),
                                Waitings.builder().userId(1L).marketId(3L).build(),
                                Waitings.builder().userId(5L).marketId(3L).build()
                        ));
        //when
        var result = waitingsReadService.getCurrentWaiting(1L);
        //then
        assertEquals(3L,result.getMarketId());
        assertEquals(3,result.getPriorTeams());
    }
}