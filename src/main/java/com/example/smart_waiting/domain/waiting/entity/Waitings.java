package com.example.smart_waiting.domain.waiting.entity;

import com.example.smart_waiting.domain.base.BaseEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@SuperBuilder
@Getter
@RedisHash("waitings")
public class Waitings extends BaseEntity {

    @Id
    private Long id;

    private Long customerId;
    private Long marketId;
}
