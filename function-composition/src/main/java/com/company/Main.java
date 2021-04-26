package com.company;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        //given the values, double the even numbers and total.

        System.out.println(
                numbers.stream()
                        .filter(e -> e % 2 == 0)
                        .mapToInt(e -> e * 2)
                        .sum());

    }
}

