package com.example.vasshayden_ce08.objects;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private int studentNumber;
    private String homeTown;

    public Student(String name, int studentNumber, String homeTown) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.homeTown = homeTown;
    }

    public Student() {

    }

    public String getName() {
        return name;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getHomeTown() {
        return homeTown;
    }

}
