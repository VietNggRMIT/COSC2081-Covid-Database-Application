package com.data.covid.model.query;

import com.data.covid.model.dto.DateRange;
import com.data.covid.model.dto.Record;
import com.data.covid.model.dto.Summary;
import com.data.covid.model.query.display.ChartDisplay;
import com.data.covid.model.query.enumeration.*;
import com.data.covid.utils.DateUtils;
import com.data.covid.utils.InputUtils;
import com.data.covid.utils.StreamUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Query {

    private final List<Record> records;
    private final Set<String> locations;

    // query params
    private String location;
    private DateRange dateRange;
    private int numRecords;
    private GroupingType groupingType;
    private int groupingValue;
    private int minGroupSize;
    private int firstMinIndex;
    private Metric metric;
    private ResultType resultType;
    private DisplayType displayType;

    public Query(List<Record> records) {
        // passing records in query, allowing the user to change input query later on
        this.records = records;
        // map to set for performance (hash search instead of strings)
        this.locations = records.stream().map(Record::getLocation).collect(Collectors.toSet());

        this.start();
    }

    public static Query withSource(List<Record> records) {
        return new Query(records);
    }

    private void start() {
        this.clear();
        for (InputType inputType : InputType.values()) this.runAction(inputType);
        this.execute();
    }

    private void edit() {
        System.out.println(this);
        // ask user after query everytime if they need to edit
        InputUtils.repeat("Finished editing", () -> {
            InputType inputType = InputUtils.getEnum("input type to edit", InputType.class);
            this.runAction(inputType);
            System.out.println(this);
        });
    }

    public void execute() {
        this.edit();
        Map<LocalDate, Record> recordsByDate = this.records.stream()
                // filter by chosen location
                .filter(record -> this.location.equals(record.getLocation()))
                // filter by chosen date range
                .filter(record -> this.dateRange.contains(record.getDate()))
                // produce date-record map for lookup record by date
                .collect(Collectors.toMap(Record::getDate, Function.identity()));

        // process group data
        // iterate chosen date range, creating a stream for all the selected dates
        List<Summary> summaries = DateUtils.dateStream(this.dateRange)
                // split date groups by chosen grouping
                .map(date -> this.groupingType.asRange(this, date)).distinct()
                // produce summary of data in group
                .map(range -> new Summary(range, this.calculateValue(range, recordsByDate)))
                // sort group to ensure correctness of accumulator
                .sorted()
                // produce summary between groups with chosen result type
                .reduce(new ArrayList<>(), this.resultType::getAccumulator, StreamUtils::listCombiner);
        // display report
        this.displayType.print(summaries);
        System.out.println();
    }

    private int calculateValue(DateRange range, Map<LocalDate, Record> recordsByDate) {
        // iterate date range to ensure no date missing in between
        return DateUtils.dateStream(range)
                // filter record that exists, ignore no record (value 0 does not affect result)
                .filter(recordsByDate::containsKey)
                // get record by date
                .map(recordsByDate::get)
                // get chosen metric
                .map(this.metric::getValue)
                // sum total, handle discrete (new total) and accumulative (up to) metric data
                // returns one sum total value
                .reduce(0, this.metric::calculateTotal);
    }

    private void runAction(InputType inputType) {
        inputType.inputAction.accept(this);
    }

    // user inputs
    private void location() {
        String instruction = "Choose country/continent: ";
        String errorText = "Country/continent not found.";
        this.location = InputUtils.getCollectionItem(instruction, errorText, this.locations, Function.identity());
        System.out.println();
    }

    private void dateRangeType() {
        DateRangeType dateRangeType = InputUtils.getEnum("date range type", DateRangeType.class);
        System.out.println();

        String instruction = "Choose a date (dd/MM/yyyy): ";
        String errorText = "Wrong date or format.";
        LocalDate date1 = InputUtils.getDate(instruction, errorText);
        System.out.println();

        LocalDate date2;
        if (dateRangeType == DateRangeType.PAIR) {
            instruction = "Choose the other date (dd/MM/yyyy): ";
            date2 = InputUtils.getDate(instruction, errorText);
        } else {
            String text = dateRangeType.name().toLowerCase().replaceFirst("_", " ");
            instruction = String.format("Number of %s the chosen date: ", text);
            errorText = "Must be positive integer.";
            int offset = InputUtils.getInt(instruction, errorText, i -> i >= 0);
            date2 = dateRangeType.toNewDate(date1, offset);
        }
        this.dateRange = new DateRange(date1, date2);
        this.numRecords = DateUtils.countInclusive(this.dateRange);
        System.out.println();
    }

    private void grouping() {
        this.groupingType = InputUtils.getEnum("grouping", GroupingType.class);
        System.out.println();

        String instruction;
        String errorText;
        switch (this.groupingType) {
            case NUMBER_OF_GROUPS:
                int maxSize = Math.min(this.numRecords, ChartDisplay.MAX_CHART_COLS);
                instruction = "Choose number of groups: ";
                errorText = String.format("Must be in range 1-%d.", maxSize);
                this.groupingValue = InputUtils.getInt(instruction, errorText, i -> i > 0 && i <= maxSize);
                this.minGroupSize = this.numRecords / this.groupingValue;
                int numLargerGroups = this.numRecords % this.groupingValue;
                this.firstMinIndex = numLargerGroups * (this.minGroupSize + 1);
                break;
            case NUMBER_OF_DAYS:
                instruction = "Choose number of days in group: ";
                errorText = String.format("Must be positive integer divided %s.", this.numRecords);
                // check to see if division have any remainders
                this.groupingValue = InputUtils.getInt(instruction, errorText, i -> i >= 0 && this.numRecords % i == 0);
                this.minGroupSize = this.groupingValue;
                break;
            case NO_GROUPING:
        }
        System.out.println();
    }

    private void metric() {
        this.metric = InputUtils.getEnum("metric", Metric.class);
        System.out.println();
    }

    private void resultType() {
        this.resultType = InputUtils.getEnum("result type", ResultType.class);
        System.out.println();
    }

    private void displayType() {
        this.displayType = InputUtils.getEnum("display type", DisplayType.class);
        System.out.println();
    }

    private void clear() {
        this.location = null;
        this.dateRange = null;
        this.numRecords = -1;
        this.groupingType = null;
        this.groupingValue = -1;
        this.minGroupSize = -1;
        this.firstMinIndex = -1;
        this.metric = null;
        this.resultType = null;
        this.displayType = null;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public int getMinGroupSize() {
        return minGroupSize;
    }

    public int getFirstMinIndex() {
        return firstMinIndex;
    }

    @Override
    public String toString() {
        return "Query{" +
                "location='" + location + '\'' +
                ", dateRange=" + dateRange +
                ", grouping=" + groupingType +
                ", groupingValue=" + groupingValue +
                ", metric=" + metric +
                ", resultType=" + resultType +
                ", displayType=" + displayType +
                '}';
    }

    private enum InputType {

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
    }
}
