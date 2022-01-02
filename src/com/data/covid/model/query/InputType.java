package com.data.covid.model.query;

import java.util.function.Consumer;

public enum InputType {

    LOCATION(Query::location),
    DATE_RANGE_TYPE(Query::dateRangeType),
    GROUPING(Query::grouping),
    METRIC(Query::metric),
    RESULT_TYPE(Query::resultType),
    DISPLAY_TYPE(Query::displayType);

    private final Consumer<Query> inputAction;

    InputType(Consumer<Query> inputAction) {
        this.inputAction = inputAction;
    }

    public Consumer<Query> getInputAction() {
        return inputAction;
    }
}
