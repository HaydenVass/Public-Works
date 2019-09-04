package com.example.bark_buddy.utils;

import android.location.Location;

public class LocationUtils {

    public static boolean checkDistance(int miles, double userLat, double userLong, double eventLat, double eventLong) {
        float radius =  1609.34f * miles;
        float[] results = new float[3];
        Location.distanceBetween(userLat, userLong, eventLat, eventLong, results);
        return (results[0] <= radius);
    }
}

