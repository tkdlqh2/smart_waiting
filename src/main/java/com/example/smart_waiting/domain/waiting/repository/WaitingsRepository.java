package com.example.smart_waiting.domain.waiting.repository;

import com.example.smart_waiting.domain.waiting.entity.Waitings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class WaitingsRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    private static final String MARKET_QUEUE_STRING = "Market queue No.";
    private static final String ALREADY_WAITING_USER_STRING = "Check set";


    public void save(Waitings w){
        redisTemplate.opsForSet().add(ALREADY_WAITING_USER_STRING,w.getUserId());
        redisTemplate.opsForList().rightPush(MARKET_QUEUE_STRING+w.getMarketId(),w.getUserId());
    }

    public boolean existsByUserId(Long userId){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(ALREADY_WAITING_USER_STRING, userId));
    }
}
