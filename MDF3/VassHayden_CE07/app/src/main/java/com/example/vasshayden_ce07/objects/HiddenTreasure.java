package com.example.vasshayden_ce07.objects;

import android.graphics.Point;

import java.io.Serializable;

public class HiddenTreasure implements Serializable {

    private final String name;
    private final int value;
    private Point point;

    public HiddenTreasure(String name, int value) {
        this.name = name;
        this.value = value;
        point = null;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return name + " " + value;
    }
}
