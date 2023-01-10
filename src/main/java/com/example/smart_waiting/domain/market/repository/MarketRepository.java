package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market,Long> {
}
