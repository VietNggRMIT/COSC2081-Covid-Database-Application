package com.data.covid.model.query.grouping;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.query.Query;

import java.time.LocalDate;

public class SingleGrouping implements Grouping {

    @Override
    public DateRange asGroup(Query query, LocalDate date) {
        return new DateRange(date);
    }
}
