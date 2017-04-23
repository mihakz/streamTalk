package com.epam.talk.stream;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mikhail_Kantserov on 21/04/2017.
 */
public class ErrorsTest {

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
    public void reduceError() {
        Integer result = list.stream()
//                .parallel()
                .reduce(0, (x, y) -> x + y);//.reduce(10, (x, y) -> x + y)
        System.out.println("Result    : " + result);
    }

    @Test
    public void findAnyNotFirstError() {
        Integer result = list.stream()
//                .parallel()
                .filter(x -> x > 10)
                .findAny().get();
        System.out.println("Result    : " + result);
    }

    @Test
    public void testNotThreadSafeStructure() {
        for (int i = 1; i < 10; i++) {
            List<String> processing = new ArrayList<>();
            list.stream()
//                    .parallel()
                    .filter(x -> x % 2 == 0)
                    .peek(x -> processing.add(String.valueOf(x)))
                    .collect(Collectors.toList());
            System.out.println("Processing: " + processing);
        }
    }

    //careful with nested parallel streams
}
