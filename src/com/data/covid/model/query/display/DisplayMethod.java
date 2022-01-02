package com.data.covid.model.query.display;

import com.data.covid.model.dto.Summary;

import java.util.List;

@FunctionalInterface
public interface DisplayMethod {

    String asString(List<Summary> summaries);
}
