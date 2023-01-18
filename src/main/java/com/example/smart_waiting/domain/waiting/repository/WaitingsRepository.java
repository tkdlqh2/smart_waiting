package com.example.smart_waiting.domain.waiting.repository;

import com.example.smart_waiting.domain.waiting.entity.Waitings;
import com.example.smart_waiting.exception.error_code.WaitingsErrorCode;
import com.example.smart_waiting.exception.exception_class.WaitingsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Waitings deleteByMarketId(Long marketId){
        var userId = redisTemplate.opsForList().leftPop(MARKET_QUEUE_STRING+marketId);

        if(!ObjectUtils.isEmpty(userId)){
            redisTemplate.opsForSet().pop(ALREADY_WAITING_USER_STRING,userId);
            return Waitings.builder()
                    .marketId(marketId)
                    .userId(userId)
                    .build();
        }

        throw new WaitingsException(WaitingsErrorCode.MARKET_QUEUE_IS_ALREADY_EMPTY);
    }

    public List<Waitings> findAllByMarketId(Long marketId, int size){
        return Objects.requireNonNull(redisTemplate.opsForList().range(MARKET_QUEUE_STRING + marketId, 0, size)).stream()
                .map(x-> Waitings.builder().userId(x).marketId(marketId).build()).collect(Collectors.toList());}
}
