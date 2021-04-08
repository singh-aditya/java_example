package com.company;

import java.util.function.Supplier;

public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton(){
        System.out.println("Singleton Instantiated..");
    }

    public static Supplier<Singleton> getInstance()
    {
        return () -> instance;
    }

    public void doSomething(){
        System.out.println("Something Done..");
    }
}
