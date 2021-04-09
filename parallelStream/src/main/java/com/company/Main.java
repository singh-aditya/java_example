package com.company;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main (String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //given the values, double the even numbers and total.

        Timeit.code(() ->
                System.out.println(
                        //numbers.stream()
                        numbers.parallelStream()
                                .filter(e -> e % 2 == 0)
                                .mapToInt(Main::compute)
                                .sum()));

    }

    public static int compute(int number) {
        //assume this is time intensive
        try { Thread.sleep(TimeUnit.SECONDS.toMillis(1)); } catch(Exception ex) {}
        return number * 2;
    }
}

