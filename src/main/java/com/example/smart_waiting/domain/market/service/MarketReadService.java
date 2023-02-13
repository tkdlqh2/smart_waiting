package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketDetails;
import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketDtoForWaiting;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.waiting.dto.WaitingsResult;
import com.example.smart_waiting.exception.exception_class.MarketException;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.MARKET_NOT_FOUND;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MarketReadService {

    private final MarketRepository marketRepository;
    private static final Long DEFAULT_WAITING_TIME_PER_TEAM = 5L;

    public PageCursor<MarketDto> getMarketsByFilter(MarketFilter filter, CursorRequest request){

        return marketRepository.findByFilter(filter,request);
    }

    public MarketDetails getMarketDetails(Long marketId) {

        Market market = marketRepository.findById(marketId)
                .orElseThrow(()-> new MarketException(MARKET_NOT_FOUND));

        return MarketDetails.of(market);
    }

    public WaitingsResult getWaitingsResult(Long marketId, int priorTeams){

        var market = marketRepository.findById(marketId)
                .orElseThrow(()-> new MarketException(MARKET_NOT_FOUND));

        Long waitingTimePerTeam = market.getWaitingTimePerTeam();
        if(ObjectUtils.isEmpty(waitingTimePerTeam)){
            waitingTimePerTeam = DEFAULT_WAITING_TIME_PER_TEAM;
        }

        return new WaitingsResult(priorTeams,
                (priorTeams+1)*waitingTimePerTeam.intValue());
    }

    public MarketDtoForWaiting getMarketByOwner(User owner) {
        return new MarketDtoForWaiting(marketRepository.findByOwner(owner)
                .orElseThrow(()-> new MarketException(MARKET_NOT_FOUND)));
    }
}
