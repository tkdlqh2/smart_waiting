package com.example.smart_waiting.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInput {

    @Email
    private String email;
    @Size(min = 2)
    private String name;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")
    private String password;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$")
    private String phone;
}
