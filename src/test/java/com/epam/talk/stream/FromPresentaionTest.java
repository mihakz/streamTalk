package com.epam.talk.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Created by Mikhail_Kantserov on 20/04/2017.
 */
public class FromPresentaionTest {

    @Test
    public void slide3() {
        List<String> list = new ArrayList<>(
                Arrays.asList("a1", "a2", "b1", "c2", "c1"));

        list.stream()                            //source
                .filter(s -> s.startsWith("c"))  //intermediate
                .map(String::toUpperCase)        //intermediate
                .sorted()                        //intermediate
                .forEach(System.out::println);   //terminal
    }

    @Test
    public void slide4() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

        strings.parallelStream()
                .filter(String::isEmpty)
                .collect(Collectors.toList());
    }

    @Test
    public void slide7() throws ExecutionException, InterruptedException {
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());

        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        List<String> result = forkJoinPool.submit(() ->
                strings.parallelStream()
                        .filter(String::isEmpty)
                        .collect(Collectors.toList())
        ).get();

        System.out.println(result);
    }

//    @Benchmark
//    public int[] measureSequential() {
//        return IntStream.range(1, COUNT + 1)
//                .map(x -> {
////                    Blackhole.consumeCPU(CONSUME);
//                    return x++;
//                })
//                .toArray();
//    }
}
