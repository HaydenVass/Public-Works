package com.example.aid_final_day.objects;

import java.io.Serializable;

public class AndroidVersion implements Serializable {

    private float versionNumber;
    private String versionName;

    public AndroidVersion(float versionNumber, String versionName) {
        this.versionNumber = versionNumber;
        this.versionName = versionName;
    }

    public float getVersionNumber() {
        return versionNumber;
    }

    public String getVersionName() {
        return versionName;
    }

    @Override
    public String toString() {
        return versionName + " " + versionNumber;
    }
}
