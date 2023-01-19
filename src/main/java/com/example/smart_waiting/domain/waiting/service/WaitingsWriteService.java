package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.user.entity.User;
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

    public int registerWaiting(User user, Long marketId){

        if(waitingsRepository.existsByUserId(user.getId())){throw new WaitingsException(ALREADY_REGISTERED_USER);}

        Waitings waitings = Waitings.builder()
                .userId(user.getId())
                .marketId(marketId)
                .build();

        waitingsRepository.save(waitings);
        return waitingsRepository.countByMarketId(marketId);
    }

}
