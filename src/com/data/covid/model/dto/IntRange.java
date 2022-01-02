package com.data.covid.model.dto;

public class IntRange extends Range<Integer> {

    public IntRange(Integer integer) {
        super(integer);
    }

    public IntRange(Integer i1, Integer i2) {
        super(i1, i2);
    }

    @Override
    protected int compare(Integer i1, Integer i2) {
        return Integer.compare(i1, i2);
    }
}
