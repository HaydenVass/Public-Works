//Hayden Vass
//Jav 2 - 1905
//People
package com.example.vasshayden_ce04.objects;

import java.io.Serializable;
import java.util.ArrayList;

public class People implements Serializable {
    //named people as to avoid confusion with "contacts"

    private String firstName;
    private String middleName;
    private String lastName;
    private String profileImage;
    private String thumbNail;

    private String primaryPhoneNum;
    private ArrayList<String> allPhoneNums;
    private final String iD;

    public People(String iD) {
        this.iD = iD;
    }


    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPrimaryPhoneNum(String primaryPhoneNum) { this.primaryPhoneNum = primaryPhoneNum; }

    public void setAllPhoneNums(ArrayList<String> allPhoneNums) { this.allPhoneNums = allPhoneNums; }

    public void setThumbNail(String thumbNail) {
        this.thumbNail = thumbNail;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    //getters
    public String getiD() { return iD; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    //public String getMiddleName() { return middleName; }

    public String getPrimaryPhoneNum() { return primaryPhoneNum; }

    public ArrayList<String> getAllPhoneNums() { return allPhoneNums; }

    public String getProfileImage() { return profileImage; }

    public String getThumbNail() { return thumbNail; }

    //constructs a full name to ignore null results from contacts
    public String getFullName() {
        if (firstName == null){firstName = " ";}
        if (middleName == null){middleName = " ";}
        if (lastName == null) {middleName = " ";}

        return firstName + " " + middleName + " " + lastName;
    }
}
