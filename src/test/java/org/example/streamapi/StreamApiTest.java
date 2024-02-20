package org.example.streamapi;

import lombok.val;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamApiTest {
    static final List<String> LIST_OF_WORDS = Arrays.asList("one", "two", "three", "four", "five");
    static final List<String> LIST_OF_NUMBERS = Arrays.asList("1", "2", "3", "4", "5");
    static final List<String> LIST_OF_CHARS = Arrays.asList("a", "b", "c", "d", "e", "f");
    static final int ELEMENTS_COUNT_FOR_RANDOM_COLLECTION = 1_000_000;

    @Test
    public void joinListsTest() {
        val expectedList = joinListsWithoutStreamApi(LIST_OF_WORDS, LIST_OF_NUMBERS);
        List<String> joinedList = StreamApiSamples.joinLists(LIST_OF_WORDS, LIST_OF_NUMBERS);
        Assert.assertEquals(joinedList, expectedList);
    }

    @Test
    public void joinMultipleListsTest() {
        val wordsAndNumber = joinListsWithoutStreamApi(LIST_OF_WORDS, LIST_OF_NUMBERS);
        val expectedList = joinListsWithoutStreamApi(wordsAndNumber, LIST_OF_CHARS);

        val joinedList = StreamApiSamples.joinMultipleLists(LIST_OF_WORDS, LIST_OF_NUMBERS, LIST_OF_CHARS);
        Assert.assertEquals(joinedList, expectedList);
    }

    private static <T> List<T> joinListsWithoutStreamApi(List<T> firstList, List<T> secondList) {
        val output = new ArrayList<>(firstList);
        output.addAll(secondList);
        return output;
    }

    @Test
    public void streamApiFilterTest() {
        val listToFilter = StreamApiSamples.joinLists(LIST_OF_WORDS, LIST_OF_NUMBERS);

        val actualFilteredString = listToFilter.stream().filter(StreamApiTest::isInteger).toList();
        Assert.assertEquals(actualFilteredString, LIST_OF_NUMBERS);
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Test
    public void streamApiPerformanceTestWithArrayList() {
        val firstList = Stream.generate(Math::random).limit(ELEMENTS_COUNT_FOR_RANDOM_COLLECTION).toList();
        val secondList = Stream.generate(Math::random).limit(ELEMENTS_COUNT_FOR_RANDOM_COLLECTION).toList();

        long totalTimeInNanosForStreamApi = getTimeForOperationInNanoseconds(() -> StreamApiSamples.joinLists(firstList, secondList));
        long totalTimeInNanosForAddAll = getTimeForOperationInNanoseconds(() -> joinListsWithoutStreamApi(firstList, secondList));

        System.out.printf("Stream Api: %,d ns%n", totalTimeInNanosForStreamApi);
        System.out.printf("AddAll: %,d ns%n", totalTimeInNanosForAddAll);
    }

    @Test
    public void streamApiPerformanceTestWithLinkedList() {
        LinkedList<Double> firstList = Stream.generate(Math::random).limit(ELEMENTS_COUNT_FOR_RANDOM_COLLECTION).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Double> secondList = Stream.generate(Math::random).limit(ELEMENTS_COUNT_FOR_RANDOM_COLLECTION).collect(Collectors.toCollection(LinkedList::new));

        long totalTimeInNanosForStreamApi = getTimeForOperationInNanoseconds(() -> StreamApiSamples.joinLists(firstList, secondList));
        long totalTimeInNanosForAddAll = getTimeForOperationInNanoseconds(() -> joinListsWithoutStreamApi(firstList, secondList));

        System.out.printf("Stream Api: %,d ns%n", totalTimeInNanosForStreamApi);
        System.out.printf("AddAll: %,d ns%n", totalTimeInNanosForAddAll);
    }

    private static long getTimeForOperationInNanoseconds(Runnable operation) {
        val start = System.nanoTime();
        operation.run();
        val end = System.nanoTime();
        return end - start;
    }
}