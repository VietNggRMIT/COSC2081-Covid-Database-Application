package com.data.covid.model;

import com.data.covid.model.dto.Record;
import com.data.covid.utils.DateUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Parser {
    public static List<Record> parseCSV(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // ignore header
            br.readLine();
            // parse each line to Record object
            return br.lines().map(Parser::toRecord).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private static Record toRecord(String line) {
        //read all the lines and set them to a record
        try {
            String[] values = line.split(",", 8);
            Record record = new Record();
            record.setLocation(values[2]);
            record.setDate(LocalDate.parse(values[3], DateUtils.INP_FORMATTER));
            record.setNewCases(parseIntValue(values[4]));
            record.setNewDeaths(parseIntValue(values[5]));
            record.setPeopleVaccinated(parseIntValue(values[6]));
            return record;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Corrupted data: " + line, e);
        }

    }
    private static Integer parseIntValue(String raw) {return raw.isEmpty() ? 0 : Integer.parseInt(raw);}
}