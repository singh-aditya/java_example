package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main (String[] args) {
        List<Person> people = createPeople();

        //create a Map with name and age as key, and the person as value.
        System.out.println(
                people.stream()
                        .collect(Collectors.toMap(
                                person -> person.getName() + "-" + person.getAge(),
                                person -> person)));

        //given a list of people, create a map where
        //their name is the key and value is all the people with that name.
        System.out.println(
                people.stream()
                        .collect(Collectors.groupingBy(Person::getName)));

        //given a list of people, create a map where
        //their name is the key and value is all the ages of people with that name
        System.out.println(
                people.stream()
                        .collect(Collectors.groupingBy(Person::getName,
                                Collectors.mapping(Person::getAge, Collectors.toList()))));
    }

    public static List<Person> createPeople() {
        return Arrays.asList(
                new Person("Sara", Gender.FEMALE, 20),
                new Person("Sara", Gender.FEMALE, 22),
                new Person("Bob", Gender.MALE, 20),
                new Person("Paula", Gender.FEMALE, 32),
                new Person("Paul", Gender.MALE, 32),
                new Person("Jack", Gender.MALE, 2),
                new Person("Jack", Gender.MALE, 72),
                new Person("Jill", Gender.FEMALE, 12)
        );
    }
}

