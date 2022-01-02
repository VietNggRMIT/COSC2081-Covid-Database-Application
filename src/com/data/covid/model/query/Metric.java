package com.data.covid.model.query;

import com.data.covid.model.dto.Record;

import java.util.function.BinaryOperator;
import java.util.function.Function;

public enum Metric {

    POSITIVE_CASES(Record::getNewCases, Math::addExact),
    DEATHS(Record::getNewDeaths, Math::addExact),
    PEOPLE_VACCINATED(Record::getPeopleVaccinated, (a, b) -> b);

    private final Function<Record, Integer> getter;
    private final BinaryOperator<Integer> reducer;

    Metric(Function<Record, Integer> getter, BinaryOperator<Integer> reducer) {
        this.getter = getter;
        this.reducer = reducer;
    }

    public Function<Record, Integer> getGetter() {
        return getter;
    }

    public BinaryOperator<Integer> getReducer() {
        return reducer;
    }
}
