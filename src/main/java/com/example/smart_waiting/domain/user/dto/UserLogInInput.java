package com.example.smart_waiting.domain.user.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLogInInput {

    private String email;
    private String password;
}
