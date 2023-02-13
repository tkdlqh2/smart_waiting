package com.example.smart_waiting.domain.waiting.entity;

import com.example.smart_waiting.domain.base.BaseEntity;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Waitings extends BaseEntity {

    private Long userId;
    private Long marketId;
}
