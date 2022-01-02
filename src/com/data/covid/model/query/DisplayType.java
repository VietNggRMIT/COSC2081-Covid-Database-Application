package com.data.covid.model.query;

import com.data.covid.model.query.display.ChartDisplay;
import com.data.covid.model.query.display.DisplayMethod;
import com.data.covid.model.query.display.TabularDisplay;

public enum DisplayType {

    TABULAR(new TabularDisplay()),
    CHART(new ChartDisplay());

    private final DisplayMethod displayMethod;

    DisplayType(DisplayMethod displayMethod) {
        this.displayMethod = displayMethod;
    }

    public DisplayMethod getDisplayMethod() {
        return displayMethod;
    }
}
