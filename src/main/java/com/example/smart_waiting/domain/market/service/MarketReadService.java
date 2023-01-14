package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarketReadService {

    private final MarketRepository marketRepository;

    public PageCursor<MarketDto> getMarketsByFilter(MarketFilter filter, CursorRequest request){

        return marketRepository.findByFilter(filter,request);
    }
}
