package com.data.covid.model.query.display;

import com.data.covid.model.dto.Summary;

import java.util.List;
import java.util.StringJoiner;

public class TabularDisplay implements DisplayMethod {

    public final int[] joints = {0, 26, 38};

    private final String separator;
    {
        StringBuilder sepBuilder = new StringBuilder();
        for (int i = 0; i <= this.joints[this.joints.length - 1]; i++) sepBuilder.append('-');
        for (int joint : joints) sepBuilder.replace(joint, joint + 1, "+");
        this.separator = sepBuilder.toString();
    }

    @Override
    public String print(List<Summary> summaries) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(this.separator);
        joiner.add("|          Range          |   Value   |");
        joiner.add(this.separator);

        for (Summary summary : summaries)
            joiner.add(String.format("| %s | %9d |", summary.getRange(), summary.getValue()));

        joiner.add(this.separator);
        return joiner.toString();
    }

}
