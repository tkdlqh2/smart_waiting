package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market,Long>, MarketRepositoryCustom {
    boolean existsByOwner(User user);
    Optional<Market> findByOwner(User user);
}
