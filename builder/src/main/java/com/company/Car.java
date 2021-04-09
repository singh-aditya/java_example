package com.company;


public class Car {
    public Car() {
        System.out.println("Car created");
    }

    public Car addEngine(String string) {
        System.out.println("addEngine");
        return this;
    }

    public Car addTransmission(String string) {
        System.out.println("add Transmission");
        return this;
    }

    public Car addWheel(String string) {
        System.out.println("add wheel");
        return this;
    }

    public Car paint(String string) {
        System.out.println("paint");
        return this;
    }

}