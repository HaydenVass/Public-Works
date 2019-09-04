package com.example.vasshayden_aid_ce06.Objects;


public class Contact {

    private final String firstName;
    private final String lastName;
    private final String phoneNum;

    public Contact(String firstName, String lastName, String phoneNum) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPhoneNum() { return phoneNum; }
}
