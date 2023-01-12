package com.example.smart_waiting.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageCursor<T> {

    private CursorRequest nextCursorRequest;
    private List<T> body;
}
