package com.example.smart_waiting.application.usecase;

import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.service.MarketWriteService;
import com.example.smart_waiting.domain.user.entity.User;
import com.example.smart_waiting.domain.user.service.UserWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegisterMarketUserUsecase {

    private final MarketWriteService marketWriteService;
    private final UserWriteService userWriteService;

    @Transactional
    public void register(User user, MarketInput parameter){
        marketWriteService.register(user,parameter);
        userWriteService.addMarketRole(user);
    }
}
