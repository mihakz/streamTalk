package com.epam.talk.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

/**
 * Created by Mikhail_Kantserov on 20/04/2017.
 */
public class FromPresentaion {

    @Test
    public void slide3() {
        List<String> list = new ArrayList<>(
                Arrays.asList("a1", "a2", "b1", "c2", "c1"));

        list.stream()                            //𝑠𝑜𝑢𝑟𝑐𝑒
                .filter(s -> s.startsWith("c"))  //𝑖𝑛𝑡𝑒𝑟𝑚𝑒𝑑𝑖𝑎𝑡𝑒
                .map(String::toUpperCase)        //𝑖𝑛𝑡𝑒𝑟𝑚𝑒𝑑𝑖𝑎𝑡𝑒
                .sorted()                        //𝑖𝑛𝑡𝑒𝑟𝑚𝑒𝑑𝑖𝑎𝑡𝑒
                .forEach(System.out::println);   //𝑡𝑒𝑟𝑚𝑖𝑛𝑎𝑙
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        System.out.println(commonPool.getParallelism());
    }

    @Test
    public void slide4() {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

        strings.parallelStream()
                .filter(String::isEmpty)
                .collect(Collectors.toList());
    }
}
