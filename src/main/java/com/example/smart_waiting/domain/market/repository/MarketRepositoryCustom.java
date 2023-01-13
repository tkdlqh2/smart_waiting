package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;

import java.util.List;

public interface MarketRepositoryCustom {

    List<Market> findByFilter(MarketFilter filter);
}
