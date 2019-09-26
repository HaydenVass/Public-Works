// Hayden Vass
// JAV1 - 1094
// CE04
package com.example.vasshaydence04;


import android.support.annotation.NonNull;

class Person {
    private final String firstName;
    private final String lastName;
    private final String birthday;
    private final int picture;

    Person(String _fName, String _lName, String _bDay, int _img){
        firstName = _fName;
        lastName = _lName;
        birthday = _bDay;
        picture = _img;
    }

    String getFirstName(){
        return firstName;
    }
    String getLastName(){
        return  lastName;
    }
    String getBirthday(){
        return birthday;
    }
    int getPicture(){
        return picture;
    }

    @Override
    @NonNull
    public String toString() {
        return firstName + " " + lastName;
    }
}
