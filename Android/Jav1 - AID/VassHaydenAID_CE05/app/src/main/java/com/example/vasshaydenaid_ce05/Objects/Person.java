package com.example.vasshaydenaid_ce05.Objects;

public class Person {

    final String firstName;
    final String lastName;


    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + "|";
    }
}
