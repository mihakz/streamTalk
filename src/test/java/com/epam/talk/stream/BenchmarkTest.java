package com.epam.talk.stream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mikhail_Kantserov on 19/04/2017.
 */
@State(Scope.Benchmark)
@Warmup(iterations = 15)
@Measurement(iterations = 15)
@Fork(0)
@BenchmarkMode(value = Mode.Throughput)
public class BenchmarkTest {

    private static final int COUNT = 20;
    private static final int CONSUME = 256;

    private List<Integer> list;

    @Setup
    public void setup() {
        Integer[] array = new Integer[COUNT];
        for (int i = 1; i <= COUNT; i++) {
            array[i - 1] = i;
        }
        list = Arrays.asList(array);
    }

    @Benchmark
    public List<Integer> measureSequential() {
        return list.stream()
                .map(x -> {
                    Blackhole.consumeCPU(CONSUME);
                    return x++;
                })
                .collect(Collectors.toList());
    }

    @Benchmark
    public List<Integer> measureParallel() {
         return list.parallelStream()
                 .map(x -> {
                     Blackhole.consumeCPU(CONSUME);
                     return x++;
                 })
                 .collect(Collectors.toList());
    }

}
