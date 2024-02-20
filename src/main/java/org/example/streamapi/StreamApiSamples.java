package org.example.streamapi;

import lombok.val;

import java.util.List;
import java.util.stream.Stream;

public final class StreamApiSamples {
    private StreamApiSamples() {
    }

    public static <T> List<T> joinLists(List<T> firstList, List<T> secondList) {
        return Stream.concat(firstList.stream(), secondList.stream()).toList();
    }

    @SafeVarargs
    public static <T> List<T> joinMultipleLists(List<T> firstList, List<T>... otherLists) {
        if (otherLists.length == 0) {
            return firstList;
        }

        var finalStream = firstList.stream();
        for (val otherList : otherLists) {
            finalStream = Stream.concat(finalStream, otherList.stream());
        }
        return finalStream.toList();
    }
}
