package ru.otus.l051;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Comparator.*;
import static org.apache.commons.lang3.ArrayUtils.addAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyArrayListTest {

    @ParameterizedTest
    @MethodSource("stringArrayPairsProvider")
    void addAllTest(String[] init, String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(init);
        Collections.addAll(myArrayList, arr);
        assertArrayEquals(addAll(init, arr), myArrayList.toArray());
    }

    @ParameterizedTest
    @MethodSource("stringArrayPairsProvider")
    void copyTest(String[] init, String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(init);
        List<String> otherList = new ArrayList<>(asList(arr));

        if (init.length > arr.length) {
            checkCopyException(otherList, myArrayList);
            checkCopySuccess(init, otherList, myArrayList);
        } else if (init.length < arr.length) {
            checkCopyException(myArrayList, otherList);
            checkCopySuccess(arr, myArrayList, otherList);
        } else {
            checkCopySuccess(init, otherList, myArrayList);
            myArrayList = new MyArrayList<>(init);
            otherList = new ArrayList<>(asList(arr));
            checkCopySuccess(arr, myArrayList, otherList);
        }
    }

    private void checkCopyException(List<String> src, List<String> dest) {
        assertThrows(
                IndexOutOfBoundsException.class,
                () -> Collections.copy(src, dest)
        );
    }

    private void checkCopySuccess(String[] destBase, List<String> src, List<String> dest) {
        Collections.copy(dest, src);
        assertArrayEquals(src.toArray(), dest.subList(0, src.size()).toArray());
        assertArrayEquals(
                copyOfRange(destBase, src.size(), dest.size()),
                dest.subList(src.size(), dest.size()).toArray()
        );
    }

    @ParameterizedTest
    @MethodSource("stringArraysProvider")
    void sortTest(String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        Collections.sort(myArrayList, naturalOrder());
        Arrays.sort(arr);
        assertArrayEquals(myArrayList.toArray(), arr);
    }

    @ParameterizedTest
    @MethodSource("stringArraysProvider")
    void size(String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        assertEquals(arr.length, myArrayList.size());
    }

    @ParameterizedTest
    @MethodSource("stringArrayAndIndexPairsProvider")
    void get(String[] arr, int index) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        if (index < 0 || index >= arr.length) {
            assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.get(index));
        } else {
            assertEquals(arr[index], myArrayList.get(index));
        }
    }

    @ParameterizedTest
    @MethodSource("stringArrayAndIndexPairsProvider")
    void set(String[] arr, int index) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        String value = "testProbe";
        if (index < 0 || index >= arr.length) {
            assertThrows(IndexOutOfBoundsException.class, () -> myArrayList.set(index, value));
        } else {
            myArrayList.set(index, value);
            assertEquals(value, myArrayList.get(index));
        }
    }

    @ParameterizedTest
    @MethodSource("stringArraysProvider")
    void add(String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        String value = "testProbe";
        myArrayList.add(value);
        assertEquals(value, myArrayList.get(myArrayList.size()-1));
        assertEquals(arr.length+1, myArrayList.size());
    }

    @ParameterizedTest
    @MethodSource("stringArraysProvider")
    void toArray(String[] arr) {
        MyArrayList<String> myArrayList = new MyArrayList<>(arr);
        assertArrayEquals(arr, myArrayList.toArray());
    }

    private static Stream<Arguments> stringArrayPairsProvider() {
        List<String[]> initialList = List.of(
                new String[]{},
                new String[]{"1"},
                new String[]{"1", "2"},
                new String[]{"1", "2", "3"}
        );
        return Stream.of(
                new String[]{},
                new String[]{"a"},
                new String[]{"a", "b"},
                new String[]{"a", "b", "c"}
        ).flatMap(arr -> initialList.stream().map(a -> Map.entry(a, arr)))
                .map(p -> Arguments.of(p.getKey(), p.getValue()));

    }

    private static Stream<Arguments> stringArraysProvider() {
        return Stream.of(
                new String[]{},
                new String[]{"1"},
                new String[]{"2", "1"},
                new String[]{"1", "2"},
                new String[]{"3", "1", "2"},
                "Hello, OTUS! Sorting test; split by char".split(""),
                "Hello, OTUS! Sorting test; split by words".split(" ")
        ).map(a -> (Object) a)
                .map(Arguments::of);
    }

    private static Stream<Arguments> stringArrayAndIndexPairsProvider() {
        List<Integer> indexList = List.of(-1, -2, 0, 1, 2, 3);
        return Stream.of(
                new String[]{},
                new String[]{"a"},
                new String[]{"a", "b"},
                new String[]{"a", "b", "c"}
        ).flatMap(arr -> indexList.stream().map(a -> Map.entry(arr, a)))
                .map(p -> Arguments.of(p.getKey(), p.getValue()));

    }

}