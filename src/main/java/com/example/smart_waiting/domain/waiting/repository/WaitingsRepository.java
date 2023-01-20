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
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class WaitingsRepository {

    private final RedisTemplate<String, Long> redisTemplate;

    private static final String MARKET_QUEUE_STRING = "Market queue No.";
    private static final Long NO_WAITING_MARKET_ID = -1L;

    public void save(Waitings w){
        redisTemplate.opsForValue().set(w.getUserId().toString(),w.getMarketId());
        redisTemplate.opsForList().rightPush(MARKET_QUEUE_STRING+w.getMarketId(),w.getUserId());
    }

    public Optional<Waitings> findByUserId(Long userId){
        var marketId = redisTemplate.opsForValue().get(userId.toString());
        if(ObjectUtils.isEmpty(marketId) || marketId.equals(-1L)){
            return Optional.empty();
        }

        return Optional.of(Waitings.builder()
                                    .userId(userId)
                                    .marketId(marketId)
                                    .build());
    }

    public Waitings deleteByMarketId(Long marketId){
        var userId = redisTemplate.opsForList().leftPop(MARKET_QUEUE_STRING+marketId);

        if(!ObjectUtils.isEmpty(userId)){
            redisTemplate.opsForValue().set(userId.toString(),NO_WAITING_MARKET_ID);
            return Waitings.builder()
                    .marketId(marketId)
                    .userId(userId)
                    .build();
        }

        throw new WaitingsException(WaitingsErrorCode.MARKET_QUEUE_IS_ALREADY_EMPTY);
    }

    public List<Waitings> findAllByMarketId(Long marketId){
        var size = countByMarketId(marketId);
        return Objects.requireNonNull(redisTemplate.opsForList().range(MARKET_QUEUE_STRING + marketId, 0, size))
                .stream().map(x-> Waitings.builder().userId(x).marketId(marketId).build()).collect(Collectors.toList());}

    public int countByMarketId(Long marketId) {
        var size = redisTemplate.opsForList().size(MARKET_QUEUE_STRING+marketId);
        if(ObjectUtils.isEmpty(size)){
            return 0;
        }else{
            return size.intValue();
        }
    }
}
