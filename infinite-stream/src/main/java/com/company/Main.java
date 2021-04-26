package com.company;

import java.util.stream.Stream;

public class Main {
    public static void main (String[] args) {
        //Given a number k, and a count n, find the total of double of n
        //even numbers starting with k, where sqrt of each number is > 20
        System.out.println(compute(10, 15));
    }

    public static int compute(int k, int n) {
        return Stream.iterate(k, e -> e + 1) // unbounded
                .filter(e -> e % 2 == 0) // unbounded
                .filter(e -> Math.sqrt(e) > 20) // unbounded
                .mapToInt(e -> e * 2) // unbounded
                .limit(n) // limit to n
                .sum();
    }
}

