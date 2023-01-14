package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.dto.MarketUpdateInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.market.type.MarketStatus;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.exception.exception_class.MarketException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.*;

@RequiredArgsConstructor
@Service
public class MarketWriteService {
    private final MarketRepository marketRepository;

    public void register(User user, MarketInput parameter){
        if(marketRepository.existsByOwner(user)){throw new MarketException(ALREADY_HAVE_MARKET);}
        Market market = new Market(user,parameter);
        marketRepository.save(market);
    }

    @Transactional
    public void updateMarket(User user, MarketUpdateInput parameter){
        Market market = marketRepository.findByOwner(user)
                .orElseThrow(() -> new MarketException(MARKET_NOT_FOUND));

        if(!market.getStatus().equals(MarketStatus.APPROVED)){
            throw new MarketException(MARKET_STATUS_IS_NOT_APPROVED);}

        market.update(parameter);
        marketRepository.save(market);
    }
}
