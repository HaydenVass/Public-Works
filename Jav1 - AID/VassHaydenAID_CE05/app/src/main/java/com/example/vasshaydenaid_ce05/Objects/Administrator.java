package com.example.vasshaydenaid_ce05.Objects;

public class Administrator extends Person {

    private final String program;

    public Administrator(String firstName, String lastName, String program) {
        super(firstName, lastName);
        this.program = program;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | " + program;
    }
}
