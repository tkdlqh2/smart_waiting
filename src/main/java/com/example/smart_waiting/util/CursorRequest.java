package com.example.smart_waiting.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CursorRequest {

    private Long key;
    private int size;

    public static final Long NONE_KEY = -1L;

    public boolean hasKey() {
        return key != null && !key.equals(NONE_KEY);
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
