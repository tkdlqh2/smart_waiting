package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketDetails;
import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.exception.exception_class.MarketException;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.MARKET_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MarketReadService {

    private final MarketRepository marketRepository;

    public PageCursor<MarketDto> getMarketsByFilter(MarketFilter filter, CursorRequest request){

        return marketRepository.findByFilter(filter,request);
    }

    public MarketDetails getMarketDetails(Long marketId) {

        Market market = marketRepository.findById(marketId)
                .orElseThrow(()-> new MarketException(MARKET_NOT_FOUND));

        return MarketDetails.of(market);
    }
}
