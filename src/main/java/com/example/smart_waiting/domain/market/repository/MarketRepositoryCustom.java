package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;

public interface MarketRepositoryCustom {

    PageCursor<MarketDto> findByFilter(MarketFilter filter, CursorRequest request);
}
