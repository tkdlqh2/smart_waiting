package com.example.smart_waiting.domain.market.entity;

import com.example.smart_waiting.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    private String name;
    private String registrationNum;
    private Long zipCode;
    private String detailAddress;

    private Long openHour;
    private Long closeHour;
}
