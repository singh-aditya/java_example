package com.company;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main (String[] args) {
        final String text = "text";
        Function<String, String> addLocalDateTime = x -> LocalDateTime.now().toString() + " " + x;
        Function<String, String> reverse = x -> new StringBuilder(x).reverse().toString();

        List<String> list =  Stream.of(String::toUpperCase, addLocalDateTime, reverse)
                .map(f -> f.apply(text))
                .collect(Collectors.toList());
        System.out.println(list);
    }
}

