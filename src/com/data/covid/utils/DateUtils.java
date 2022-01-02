package com.data.covid.utils;

import com.data.covid.model.dto.DateRange;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

public class DateUtils {

    private DateUtils() {}

    public static final DateTimeFormatter INP_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
    public static final DateTimeFormatter OUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static int countInclusive(DateRange range) {
        return (int) ChronoUnit.DAYS.between(range.getStart(), range.getEnd().plusDays(1));
    }

    public static Stream<LocalDate> dateStream(DateRange range) {
        return Stream.iterate(range.getStart(), d -> d.plusDays(1)).limit(countInclusive(range));
    }
}
