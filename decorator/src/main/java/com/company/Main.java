package com.company;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main (String[] args) {
        final String text = "text";

        Consumer<String> toASCII = str -> System.out.println("ASCII: " + str);
        Function<String, String> convertToHex = str -> str.chars()
                                                        .boxed()
                                                        .map(x -> "0x" + Integer.toHexString(x))
                                                        .collect(Collectors.joining(" "));

        Consumer<String> toHex = str -> System.out.println("HEX: " + convertToHex.apply(str));

        toASCII.andThen(toHex).accept(text);

        // Using User defined Interface
        PrintText printASCII = str -> System.out.println("ASCII: " + str);
        PrintText printHex = str -> System.out.println("HEX: " + convertToHex.apply(str));

        printASCII.andThen(printHex).print(text);

    }
}

@FunctionalInterface
interface PrintText {
    void print(String text);

    default PrintText andThen(PrintText after) {
        Objects.requireNonNull(after);
        return t -> { print(t); after.print(t); };
    }
}
