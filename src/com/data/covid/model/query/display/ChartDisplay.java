package com.data.covid.model.query.display;

import com.data.covid.model.dto.IntRange;
import com.data.covid.model.dto.Range;
import com.data.covid.model.dto.Summary;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ChartDisplay implements DisplayMethod {

    public static final int MAX_CHART_COLS = 79;
    public static final int MAX_CHART_ROWS = 23;

    private static final int ROW_OFFSET = 3;
    private static final int OFFSET = 1;

    private final char[][] chart = this.getDefaultBoard();

    @Override
    public String asString(List<Summary> summaries) {
        Map<Integer, IntRange> yValues = new HashMap<>();
        // plot point
        if (!summaries.isEmpty()) {
            // evenly spaced column
            int colSpacing = MAX_CHART_COLS / summaries.size();
            int min = summaries.stream().mapToInt(Summary::getValue).min().orElse(0);
            int max = summaries.stream().mapToInt(Summary::getValue).max().orElse(0);
            // get scale, handle constant values
            float scale = max == min ? 0 : (float) MAX_CHART_ROWS / (max - min);
            for (int i = 0; i < summaries.size(); i++) {
                int col = OFFSET + colSpacing * i;
                // evenly spaced row
                int raw = summaries.get(i).getValue();
                int row = Math.round(scale * (raw - min));
                row = Math.min(row, MAX_CHART_ROWS - 1);
                row += ROW_OFFSET;
                // adjust value range label of row
                IntRange bounds = yValues.computeIfAbsent(row, v -> new IntRange(raw));
                yValues.put(row, new IntRange(Math.min(bounds.getStart(), raw), Math.max(bounds.getEnd(), raw)));
                // add col label
                if (i < 10) {
                    this.chart[1][col] = (char) (i + '0');
                } else {
                    this.chart[1][col] = (char) (i / 10 + '0');
                    this.chart[0][col] = (char) (i % 10 + '0');
                }
                // plot data
                this.chart[row][col] = '*';
            }
        }
        // draw chart
        String chartString = IntStream.iterate(ROW_OFFSET - 1 + MAX_CHART_ROWS, i -> i - 1)
                .limit(ROW_OFFSET + MAX_CHART_ROWS)
                .mapToObj(i -> String.format("%21s%s",
                        // add row label
                        Optional.ofNullable(yValues.get(i)).map(Range::toString).orElse(""),
                        new String(this.chart[i])))
                .collect(Collectors.joining("\n"));

        // add legend
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(chartString).add("Legend:");
        for (int i = 0; i < summaries.size(); i++) joiner.add(String.format("%2d. %21s", i, summaries.get(i).getRange()));
        return joiner.toString();
    }

    private char[][] getDefaultBoard() {
        char[][] chart = new char[ROW_OFFSET + MAX_CHART_ROWS][OFFSET + MAX_CHART_COLS];
        // fill col label
        for (int j = 0; j <= MAX_CHART_COLS; j++) {
            for (int i = 0; i < ROW_OFFSET - OFFSET; i++) chart[i][j] = ' ';
        }
        // fill origin
        chart[ROW_OFFSET - OFFSET][0] = '|';
        // fill x axis
        for (int j = OFFSET; j <= MAX_CHART_COLS; j++) chart[ROW_OFFSET - OFFSET][j] = '_';
        for (int i = OFFSET; i <= MAX_CHART_ROWS; i++) {
            // fill y axis
            chart[ROW_OFFSET - OFFSET + i][0] = '|';
            // fill value board
            for (int j = OFFSET; j <= MAX_CHART_COLS; j++) chart[ROW_OFFSET - OFFSET + i][j] = ' ';
        }
        return chart;
    }
}
