package com.data.covid.model.query.enumeration;

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

    public int getValue(Record record) {
        return this.getter.apply(record);
    }

    public int calculateTotal(int a, int b) {
        return this.reducer.apply(a, b);
    }
}
