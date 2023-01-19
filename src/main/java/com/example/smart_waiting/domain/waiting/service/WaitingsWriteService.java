package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.domain.waiting.repository.WaitingsRepository;
import com.example.smart_waiting.exception.exception_class.WaitingsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.smart_waiting.exception.error_code.WaitingsErrorCode.ALREADY_REGISTERED_USER;

@Service
@RequiredArgsConstructor
public class WaitingsWriteService {

    private final WaitingsRepository waitingsRepository;

    public int registerWaiting(Long userId, Long marketId){

        if(waitingsRepository.existsByUserId(userId)){throw new WaitingsException(ALREADY_REGISTERED_USER);}

        Waitings waitings = Waitings.builder()
                .userId(userId)
                .marketId(marketId)
                .build();

        waitingsRepository.save(waitings);
        return waitingsRepository.countByMarketId(marketId);
    }

}
