package com.data.covid.model.query.grouping;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.query.Query;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CountGrouping implements Grouping {

    @Override
    public DateRange asGroup(Query query, LocalDate date) {
        long offset = ChronoUnit.DAYS.between(query.getDateRange().getStart(), date);
        int minGroupSize = query.getMinGroupSize();
        int firstMinIndex = query.getFirstMinIndex();
        int groupSize;
        LocalDate start = query.getDateRange().getStart();
        if (offset < firstMinIndex) {
            // large group
            groupSize = minGroupSize + 1;
            long partitionIndex = offset / groupSize;
            start = start.plusDays(groupSize * partitionIndex);
        } else {
            // small group
            groupSize = minGroupSize;
            long partitionIndex = (offset - firstMinIndex + 1) / groupSize;
            start = start.plusDays(firstMinIndex + groupSize * partitionIndex);
        }
        return new DateRange(start, start.plusDays(groupSize - 1));
    }
}
