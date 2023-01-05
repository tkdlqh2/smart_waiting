package com.example.smart_waiting.domain.user.entity;

import com.example.smart_waiting.domain.user.type.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false,unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "phone", nullable = false,unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String authKey;
    private LocalDateTime expireDateTime;

    public void approve(){
        this.userStatus = UserStatus.APPROVED;
    }
}
