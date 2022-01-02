package com.data.covid;

import com.data.covid.model.Parser;
import com.data.covid.model.query.Query;
import com.data.covid.utils.InputUtils;

public class Main {
    public static void main(String[] args) {
        // build query
        Query query = Query.withSource(Parser.parseCSV("src/covid-data.csv"));
        // repeat if user is done after program executes
        InputUtils.repeat("Done", query::execute);
    }
}
