package com.epam.talk.stream;

import org.junit.Before;
import org.junit.Test;
import org.openjdk.jmh.annotations.Setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mikhail_Kantserov on 21/04/2017.
 */
public class StreamTest {

    private List<Integer> list;
    private static final int COUNT = 20;

    @Before
    public void setup() {
        Integer[] array = new Integer[COUNT];
        for (int i = 1; i <= COUNT; i++) {
            array[i - 1] = i;
        }
        list = Arrays.asList(array);
    }

    @Test
    public void testSequential() {
        List<String> processing = new ArrayList<>();
        List<String> result = list.stream()
                .filter(x -> x % 2 == 0)
                .peek(x -> processing.add(String.valueOf(x)))
                .map(String::valueOf)
                .collect(Collectors.toList());
        System.out.println("Processing: " + processing);
        System.out.println("Result    : " + result);
    }

    @Test
    public void testParallel() {
        List<String> processing = Collections.synchronizedList(new ArrayList<>());
        List<String> result = list.stream()
                .parallel()
                .filter(x -> x % 2 == 0)
                .peek(x -> processing.add(String.valueOf(x)))
                .map(String::valueOf)
                .collect(Collectors.toList());
        System.out.println("Processing: " + processing);
        System.out.println("Result    : " + result);
    }

    @Test
    public void testParallelThenSequential() {
        List<String> processing = Collections.synchronizedList(new ArrayList<>());
        List<String> result = list.stream()
                .parallel()
                .filter(x -> x % 2 == 0)
                .peek(x -> processing.add(String.valueOf(x)))
                .sequential()
                .map(String::valueOf)
                .collect(Collectors.toList());
        System.out.println("Processing: " + processing);
        System.out.println("Result    : " + result);
    }
}
