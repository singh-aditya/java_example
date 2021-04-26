package com.company;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //given the values, concat numbers.
        System.out.println(
                numbers.stream()
                        .map(String::valueOf)
                        // .reduce("", (carry, str) -> carry.concat(str)));
                        .reduce("", String::concat));

        //given the values, double the even numbers as "double" and total.
        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .map(e -> e * 2.0)
                        .reduce(0.0, (carry, e) -> carry + e));

        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .mapToDouble(e -> e * 2.0)
                        .sum());

    }
}

