package com.data.covid.model.query.enumeration;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.query.Query;
import com.data.covid.model.query.grouping.CountGrouping;
import com.data.covid.model.query.grouping.Grouping;
import com.data.covid.model.query.grouping.SingleGrouping;
import com.data.covid.model.query.grouping.SizeGrouping;

import java.time.LocalDate;

public enum GroupingType {

    NO_GROUPING(new SingleGrouping()),
    NUMBER_OF_GROUPS(new CountGrouping()),
    NUMBER_OF_DAYS(new SizeGrouping());

    private final Grouping grouping;

    GroupingType(Grouping grouping) {
        this.grouping = grouping;
    }

    public DateRange asRange(Query query, LocalDate date) {
        return this.grouping.asGroup(query, date);
    }
}
