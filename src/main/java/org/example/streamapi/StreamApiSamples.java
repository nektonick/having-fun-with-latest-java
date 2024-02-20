package org.example.streamapi;

import java.util.List;
import java.util.stream.Stream;

public final class StreamApiSamples {
    private StreamApiSamples() {
    }

    public static <T> List<T> joinLists(List<T> firstList, List<T> secondList) {
        return Stream.concat(firstList.stream(), secondList.stream()).toList();
    }
}
