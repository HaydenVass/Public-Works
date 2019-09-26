package com.example.vasshayden_ce01.objects;

import java.io.Serializable;

public class SavedLocation implements Serializable {

    private final String name;
    private final String description;
    private final String uri;
    private final double latitude;
    private final double longitutde;

    public SavedLocation(String name, String description, String uri, double latitude, double longitutde) {
        this.name = name;
        this.description = description;
        this.uri = uri;
        this.latitude = latitude;
        this.longitutde = longitutde;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitutde() {
        return longitutde;
    }

    @Override
    public String toString() {
        return "SavedLocation{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", uri=" + uri +
                ", latitude=" + latitude +
                ", longitutde=" + longitutde +
                '}';
    }
}
