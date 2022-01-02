package com.data.covid.model.query.enumeration;

import java.time.LocalDate;
import java.util.function.BiFunction;

public enum DateRangeType {

    PAIR(null),
    DAYS_BEFORE(LocalDate::minusDays),
    DAYS_AFTER(LocalDate::plusDays),
    WEEKS_BEFORE(LocalDate::minusWeeks),
    WEEKS_AFTER(LocalDate::plusWeeks);

    private final BiFunction<LocalDate, Long, LocalDate> skipper;

    DateRangeType(BiFunction<LocalDate, Long, LocalDate> skipper) {
        this.skipper = skipper;
    }

    public LocalDate toNewDate(LocalDate start, long offset) {
        return this.skipper.apply(start, offset);
    }
}
