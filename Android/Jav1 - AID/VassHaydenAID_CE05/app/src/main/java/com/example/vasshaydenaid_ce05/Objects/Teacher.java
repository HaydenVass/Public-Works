package com.example.vasshaydenaid_ce05.Objects;

public class Teacher extends Person {

    private final String course;

    public Teacher(String firstName, String lastName, String course) {
        super(firstName, lastName);
        this.course = course;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | " + course;
    }
}
