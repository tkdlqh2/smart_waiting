package com.example.smart_waiting.domain.market.entity;

import com.example.smart_waiting.domain.base.BaseEntity;
import com.example.smart_waiting.domain.market.dto.MarketInput;
import com.example.smart_waiting.domain.market.type.MarketStatus;
import com.example.smart_waiting.domain.user.entity.User;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.example.smart_waiting.domain.market.type.MarketStatus.UNAPPROVED;

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

    @Enumerated(EnumType.STRING)
    private MarketStatus status;

    public Market(){
        super(LocalDateTime.now(),null);
    }

    public Market(User user, MarketInput parameter){
        this.owner = user;
        this.name = parameter.getName();
        this.registrationNum = parameter.getRegistrationNum();
        this.zipCode = parameter.getZipCode();
        this.detailAddress = parameter.getDetailAddress();
        this.openHour = parameter.getOpenHour();
        this.closeHour = parameter.getCloseHour();
        this.status = UNAPPROVED;
    }
}
