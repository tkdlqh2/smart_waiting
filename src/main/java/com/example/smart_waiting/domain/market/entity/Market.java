package com.example.smart_waiting.domain.market.entity;

import com.example.smart_waiting.domain.base.BaseEntity;
import com.example.smart_waiting.domain.user.entity.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@SuperBuilder
@Entity
public class Market extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String registrationNum;
    @Column(nullable = false)
    private Long zipCode;
    @Column(nullable = false)
    private String detailAddress;
    @Column(nullable = false)
    private Long openHour;
    @Column(nullable = false)
    private Long closeHour;

    public Market(){
        super(LocalDateTime.now(),null);
    }
}
