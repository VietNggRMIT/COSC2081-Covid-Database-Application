package com.data.covid.model.dto;

import com.data.covid.utils.DateUtils;

import java.time.LocalDate;

public class DateRange extends Range<LocalDate> {

    public DateRange(LocalDate date) {
        super(date);
    }

    public DateRange(LocalDate d1, LocalDate d2) {
        super(d1, d2);
    }

    @Override
    protected String valueToString(LocalDate date) {
        return date.format(DateUtils.OUT_FORMATTER);
    }

    @Override
    protected int compare(LocalDate t1, LocalDate t2) {
        return t1.compareTo(t2);
    }
}
