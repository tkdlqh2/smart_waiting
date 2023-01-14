package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.dto.MarketDto;
import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.entity.QMarket;
import com.example.smart_waiting.domain.market.type.ParkType;
import com.example.smart_waiting.util.CursorRequest;
import com.example.smart_waiting.util.PageCursor;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketRepositoryCustomImpl extends QuerydslRepositorySupport implements
 MarketRepositoryCustom {

    public MarketRepositoryCustomImpl(){
        super(Market.class);
    }

    @Override
    public PageCursor<MarketDto> findByFilter(MarketFilter filter, CursorRequest request) {

        QMarket market = QMarket.market;
        List<MarketDto> body;
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

        if(Boolean.TRUE.equals(request.hasKey())){
            builder.and(market.id.lt(request.getKey()));
        }

        body = from(market)
                .select(Projections.fields(MarketDto.class,
                        market.name,market.rcate2,market.foodType))
                .where(builder)
                .limit(request.getSize())
                .orderBy(market.id.desc())
                .fetch();

        Long lastKey = getLastKey(body);

        return new PageCursor<>(request.next(lastKey),body);
    }

    private long getLastKey(List<MarketDto> body) {
        return body.stream().mapToLong(MarketDto::getId).max().orElse(CursorRequest.NONE_KEY);
    }
}
