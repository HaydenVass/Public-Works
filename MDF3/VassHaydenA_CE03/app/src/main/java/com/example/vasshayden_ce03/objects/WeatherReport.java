package com.example.vasshayden_ce03.objects;

import java.io.Serializable;

public class WeatherReport implements Serializable {
    private final String icon;
    private final String conditions;
    private final int tempurature;
    private final int tempHigh;
    private final int tempLow;
    private final String time;

    public WeatherReport(String icon, String conditions, int tempurature, String time) {
        this.icon = icon;
        this.conditions = conditions;
        this.tempurature = tempurature;
        this.time = time;
        tempHigh = 0;
        tempLow = 0;
    }

    public WeatherReport(String icon, String conditions, int tempurature,
                         int tempHigh, int tempLow, String time) {
        this.icon = icon;
        this.conditions = conditions;
        this.tempurature = tempurature;
        this.tempHigh = tempHigh;
        this.tempLow = tempLow;
        this.time = time;
    }

    public String getIcon() {
        return icon;
    }

    public String getConditions() {
        return conditions;
    }

    public int getTempurature() {
        return tempurature;
    }

    public String getTime() {
        return time;
    }

    public int getTempHigh() {
        return tempHigh;
    }

    public int getTempLow() {
        return tempLow;
    }

    @Override
    public String toString() {
        return "WeatherReport{" +
                "icon='" + icon + '\'' +
                ", conditions='" + conditions + '\'' +
                ", tempurature=" + tempurature +
                ", time='" + time + '\'' +
                '}';
    }
}
