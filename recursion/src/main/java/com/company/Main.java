package com.company;

import com.aol.cyclops.trampoline.Trampoline;

import java.util.stream.Stream;

public class Main {
    public static void main (String[] args) {
        new Main().fib();
    }

    public void fib()
    {
        Stream.iterate(0, x -> x+1)
                .limit(10)
                .forEach(i -> System.out.println(fibonacci(i, 0L, 1L).result()));
    }
    public Trampoline<Long> fibonacci(Integer count, Long a, Long b)
    {
        return count == 0 ? Trampoline.done(a) :
                Trampoline.more(()->fibonacci (count - 1, b, a + b));
    }

}
