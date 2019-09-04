package com.example.vasshayden_ce05;

class Person {

    private final String firstName;
    private final String lastName;
    private final int ID;
    private final String birthday;
    private final int img;
    private final String description;

    public Person(String _firstName, String _lastName, int _id, String _birthday, int _img, String _description){
        firstName = _firstName;
        lastName = _lastName;
        ID = _id;
        birthday = _birthday;
        img = _img;
        description = _description;
    }

    String getBirthday(){
        return birthday;
    }
    int getImg(){
        return img;
    }
    int getID() { return ID; }
    String getDescription() {return description;}

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
