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
}
