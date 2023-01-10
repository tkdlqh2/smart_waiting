package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.exception.exception_class.MarketException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.smart_waiting.exception.error_code.MarketErrorCode.ALREADY_HAVE_MARKET;

@RequiredArgsConstructor
@Service
public class MarketWriteService {
    private final MarketRepository marketRepository;

    public void register(User user, MarketInput parameter){
        if(marketRepository.existsByOwner(user)){throw new MarketException(ALREADY_HAVE_MARKET);}
        Market market = new Market(user,parameter);
        marketRepository.save(market);
    }
}
