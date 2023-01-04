package com.example.smart_waiting.domain.user.dto;


import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInput {

    private String email;
    private String name;
    private String password;
    private String phone;
}
