package com.data.covid.model.query.grouping;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.query.Query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SizeGrouping implements Grouping {

    @Override
    public DateRange asGroup(Query query, LocalDate date) {
        long offset = ChronoUnit.DAYS.between(query.getDateRange().getStart(), date);
        int groupSize = query.getMinGroupSize();
        long groupIndex = offset / groupSize;
        LocalDate start = query.getDateRange().getStart().plusDays(groupSize * groupIndex);
        return new DateRange(start, start.plusDays(groupSize - 1));
    }
}
