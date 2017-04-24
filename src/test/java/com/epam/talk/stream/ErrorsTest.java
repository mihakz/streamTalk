package com.epam.talk.stream;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void testNotThreadSafeStructureAndStatefull() {
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

    @Test
    public void testWithImpact() {
        Tuple<BigInteger, BigInteger> seed = new Tuple<>(BigInteger.ONE, BigInteger.ONE);
        UnaryOperator<Tuple<BigInteger, BigInteger>> f = x -> new Tuple<>(x.second, x.first.add(x.second));
        Stream.iterate(seed, f)//.parallel()
                .map(x -> x.first)
                .peek(System.out::println)
                .filter(x -> x.intValue() > 1_000_000)
                .findAny().get();
    }

    class Tuple<T, U> {
        public final T first;
        public final U second;
        public Tuple(T t, U u) {
            this.first = t;
            this.second = u;
        }
    }

    //careful with nested parallel streams
}
