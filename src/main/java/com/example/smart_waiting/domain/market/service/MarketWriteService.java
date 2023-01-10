package com.example.smart_waiting.domain.market.service;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.repository.MarketRepository;
import com.example.smart_waiting.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarketWriteService {
    private final MarketRepository marketRepository;

    public void register(User user, MarketInput parameter){
        Market market = new Market(user,parameter);
        marketRepository.save(market);
    }
}
