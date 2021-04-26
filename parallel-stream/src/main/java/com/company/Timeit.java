package com.company;

import java.util.concurrent.TimeUnit;

public class Timeit {
    public static void code(Runnable block) {
        long start = System.nanoTime();
        try {
            block.run();
        } finally {
            long end = System.nanoTime();
            System.out.println("Time taken(s): " + TimeUnit.SECONDS.convert(end - start, TimeUnit.NANOSECONDS));
        }
    }
}