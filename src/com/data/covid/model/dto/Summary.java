package com.data.covid.model.dto;

import java.util.Comparator;

public class Summary implements Comparable<Summary> {

    private static final Comparator<Summary> COMPARATOR = Comparator.comparing(s -> s.range.getStart());

    private final DateRange range;
    private int value;

    public Summary(DateRange range, int value) {
        this.range = range;
        this.value = value;
    }

    public DateRange getRange() {
        return range;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Summary{" +
                "range=" + range +
                ", value=" + value +
                '}';
    }

    @Override
    public int compareTo(Summary that) {
        return COMPARATOR.compare(this, that);
    }
}
