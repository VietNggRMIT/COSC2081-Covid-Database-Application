package com.data.covid.utils;

import java.util.List;

public class StreamUtils {

    private StreamUtils() {
    }

    public static <T> List<T> listCombiner(List<T> l1, List<T> l2) {
        l1.addAll(l2);
        return l1;
    }
}
