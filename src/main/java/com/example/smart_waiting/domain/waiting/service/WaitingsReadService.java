package com.example.smart_waiting.domain.waiting.service;

import com.example.smart_waiting.domain.waiting.dto.CurrentWaitingResult;
import com.example.smart_waiting.domain.waiting.repository.WaitingsRepository;
import com.example.smart_waiting.exception.error_code.WaitingsErrorCode;
import com.example.smart_waiting.exception.exception_class.WaitingsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WaitingsReadService {

    private final WaitingsRepository waitingsRepository;

    public CurrentWaitingResult getCurrentWaiting(Long userId) {
        var waiting = waitingsRepository.findByUserId(userId)
                .orElseThrow(() -> new WaitingsException(WaitingsErrorCode.NOT_WAITING_USER));

        var wholeList = waitingsRepository.findAllByMarketId(waiting.getMarketId());
        return new CurrentWaitingResult(waiting.getMarketId(),
                (int)wholeList.stream().takeWhile(x->!x.getUserId().equals(userId)).count());
    }
}
