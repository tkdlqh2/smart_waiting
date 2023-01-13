package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.entity.QMarket;
import com.example.smart_waiting.domain.market.type.ParkType;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketRepositoryCustomImpl extends QuerydslRepositorySupport implements
 MarketRepositoryCustom {

    public MarketRepositoryCustomImpl(){
        super(Market.class);
    }

    @Override
    public PageCursor<Market> findByFilter(MarketFilter filter, CursorRequest request) {

        QMarket market = QMarket.market;
        List<Market> body;
        BooleanBuilder builder = new BooleanBuilder();

        if(!ObjectUtils.isEmpty(filter)){

            var filterRcate2s = filter.getRcate2s().stream().filter(StringUtils::hasText)
                    .collect(Collectors.toList());

            if(!filterRcate2s.isEmpty()){
                builder.and(market.rcate2.in(filterRcate2s));
            }

            if(!filter.getFoodTypes().isEmpty()){
                builder.and(market.foodType.in(filter.getFoodTypes()));
            }

            if(!filter.isNoParkingLotOk()){
                builder.and(market.parkType.notIn(ParkType.FORBIDDEN));
            }
        }

        if(request.hasKey()){
            builder.and(market.id.lt(request.getKey()));
        }

        body = from(market)
                .where(builder)
                .limit(request.getSize())
                .orderBy(market.id.desc())
                .fetch();

        Long lastKey = getLastKey(body);

        return new PageCursor<>(new CursorRequest(lastKey, request.getSize()),body);
    }

    private long getLastKey(List<Market> body) {
        return body.stream().mapToLong(Market::getId).max().orElse(CursorRequest.NONE_KEY);
    }
}
