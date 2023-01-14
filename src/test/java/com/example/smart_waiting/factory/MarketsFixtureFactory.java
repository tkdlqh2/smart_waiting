package com.example.smart_waiting.factory;

import com.example.smart_waiting.domain.market.entity.Market;
import com.example.smart_waiting.domain.market.type.FoodType;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.misc.EnumRandomizer;
import org.jeasy.random.randomizers.range.LongRangeRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MarketsFixtureFactory {

    public static Market create() {
        var parameter = new EasyRandomParameters()
                .randomize(String.class, new StringRandomizer())
                .stringLengthRange(1, 10)
                .randomize(Long.class, new LongRangeRandomizer(1L, 100L))
                .randomize(FoodType.class, new EnumRandomizer<>(FoodType.class))
                .collectionSizeRange(0,2);
        return new EasyRandom(parameter).nextObject(Market.class);
    }

    public static List<Market> createLists (int size){

        Set<Long> idSet = new HashSet<>();
        Set<Market> marketSet = new HashSet<>();
        while(idSet.size()<size){

            Market marketCur = MarketsFixtureFactory.create();
            if(idSet.contains(marketCur.getId())){
                continue;
            }
            idSet.add(marketCur.getId());
            marketSet.add(marketCur);
        }

        return new ArrayList<>(marketSet);
    }

}
