package com.example.vasshayden_aidce08.objects;

import java.io.Serializable;

public class Monster implements Serializable {
    private final String name;
    //private boolean hasFur;
    private final String numberOfLegs;
    private final String hasFurStr;

    public Monster(String name, boolean hasFur, String numberOfLegs) {
        this.name = name;
        //this.hasFur = hasFur;
        this.numberOfLegs = numberOfLegs;

        if(hasFur){
            hasFurStr = "Yes";
        }else{
            hasFurStr = "No";
        }
    }

    public String getName() {
        return name;
    }

    public String getNumberOfLegs() {
        return numberOfLegs;
    }

    public String getHasFurStr() {
        return hasFurStr;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object m) {

        if(m==null)
            return false;
        if(! (m instanceof Monster))
            return false;

        if(((Monster) m).getName().equals(this.getName()) &&
        ((Monster) m).getHasFurStr().equals(this.getHasFurStr())&&
        ((Monster) m).getNumberOfLegs().equals(this.getNumberOfLegs())) return true;


        return super.equals(m);
    }
}
