package com.example.smart_waiting.domain.market.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MarketWaitingTeamsRepository {

    private final RedisTemplate<String,Long> redisTemplate;
    private static final String MARKET_LIST_STRING = "Market string ";
    private static final String EXPECTED_TIME_STRING = "Expected time ";
    private static final Long DEFAULT_EXPECTED_MINUTES_PER_TEAM = 5L;

    public Long getWaitingTeamsNum(Long marketId){
        return Optional.ofNullable(
                redisTemplate.opsForList().size(MARKET_LIST_STRING+marketId))
                .orElse(0L);
    }

    public Long getExpectedTimePerTeam(Long marketId){
        return Optional.ofNullable(redisTemplate.opsForValue().get(EXPECTED_TIME_STRING+marketId))
                .orElse(DEFAULT_EXPECTED_MINUTES_PER_TEAM);
    }

}
