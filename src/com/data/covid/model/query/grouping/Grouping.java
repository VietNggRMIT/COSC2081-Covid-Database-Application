package com.data.covid.model.query.grouping;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.query.Query;

import java.time.LocalDate;

@FunctionalInterface
public interface Grouping {

    DateRange asGroup(Query query, LocalDate date);
}
