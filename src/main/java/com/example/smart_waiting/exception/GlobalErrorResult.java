package com.example.smart_waiting.exception;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GlobalErrorResult {

    private String errorMessage;

    public static GlobalErrorResult of(String message) {
        return GlobalErrorResult.builder()
                .errorMessage(message)
                .build();
    }
}
