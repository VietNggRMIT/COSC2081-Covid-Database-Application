package com.data.covid.model.query.enumeration;

import com.data.covid.model.dto.Summary;
import com.data.covid.model.query.display.ChartDisplay;
import com.data.covid.model.query.display.DisplayMethod;
import com.data.covid.model.query.display.TabularDisplay;

import java.util.List;

public enum DisplayType {

    TABULAR(new TabularDisplay()),
    CHART(new ChartDisplay());

    private final DisplayMethod displayMethod;

    DisplayType(DisplayMethod displayMethod) {
        this.displayMethod = displayMethod;
    }

    public void print(List<Summary> summaries) {
        System.out.println(this.displayMethod.asString(summaries));
    }
}
