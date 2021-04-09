package com.company;

import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        Supplier<Car> carBuilder = Car::new;
        BiFunction<Car, String, Car> addFourWheels = (car, wheel) -> {
            IntStream.rangeClosed(1, 4)
                    .forEach((i) -> car.addWheel(wheel));
            return car;
        };
        Car car = carBuilder.get()
                .addEngine("Electric 150 kW")
                .addTransmission("Manual")
                .paint("red");
        addFourWheels.apply(car, "20x12x30");
    }
}