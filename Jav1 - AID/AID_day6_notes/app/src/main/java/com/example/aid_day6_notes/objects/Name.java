package com.example.aid_day6_notes.objects;

public class Name {

    String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() { return name; }


    @Override
    public String toString() {
        return name;
    }
}
