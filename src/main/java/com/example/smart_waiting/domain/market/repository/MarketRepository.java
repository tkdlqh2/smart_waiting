package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketRepository extends JpaRepository<Market,Long>, MarketRepositoryCustom {
    boolean existsByOwner(User user);
}
