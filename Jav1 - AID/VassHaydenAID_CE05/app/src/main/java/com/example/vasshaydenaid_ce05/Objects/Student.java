package com.example.vasshaydenaid_ce05.Objects;

public class Student extends Person {

    private final String id;

    public Student(String firstName, String lastName, String id) {
        super(firstName, lastName);
        this.id = id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | " + id;
    }
}
