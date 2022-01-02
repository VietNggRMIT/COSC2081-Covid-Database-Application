package com.data.covid.model.query.enumeration;

import com.data.covid.model.dto.Summary;

import java.util.List;
import java.util.function.BiFunction;

public enum ResultType {

    NEW_TOTAL((list, summary) -> {
        // discrete
        list.add(summary);
        return list;
    }),
    UP_TO((list, summary) -> {
        // accumulate
        if (!list.isEmpty()) summary.setValue(summary.getValue() + list.get(list.size() - 1).getValue());
        list.add(summary);
        return list;
    });

    private final BiFunction<List<Summary>, Summary, List<Summary>> accumulator;

    ResultType(BiFunction<List<Summary>, Summary, List<Summary>> accumulator) {
        this.accumulator = accumulator;
    }

    public List<Summary> getAccumulator(List<Summary> list, Summary summary) {
        return this.accumulator.apply(list, summary);
    }
}
