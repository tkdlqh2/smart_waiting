package com.example.smart_waiting.domain.market.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketInput {
    @NotBlank
    private String name;
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}$")
    private String registrationNum;
    @NotBlank
    private String rcate1;
    @NotBlank
    private String rcate2;
    @NotBlank
    @Size(max = 50)
    private String detailAddress;
    @Min(0)
    @Max(23)
    private Long openHour;
    @Min(0)
    @Max(23)
    private Long closeHour;
}
