package com.example.smart_waiting.domain.market.repository;

import com.example.smart_waiting.domain.market.dto.MarketFilter;
import com.example.smart_waiting.util.CursorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MarketRepositoryCustomImplTest {

    @Autowired
    private MarketRepository marketRepository;

    @Test
    void findByFilter() {

        //given
        MarketFilter filter = MarketFilter.builder()
                .rcate2s(Collections.emptyList())
                .foodTypes(Collections.emptyList())
                .noParkingLotOk(true)
                .build();
        CursorRequest cursorRequest = new CursorRequest(10L,20);
        //when
        var result = marketRepository.findByFilter(filter,cursorRequest);
        //then
        assertEquals(0, result.getBody().size());

    }
}