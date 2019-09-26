package com.example.bark_buddy.objects;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Event implements Serializable {

    private String id;
    private byte[] imgBytes;
    private String energyLevel;
    private String weightRange;
    private String host;
    private String date;
    private String startTime;
    private String endTime;
    private String location;
    private String description;
    private String triggers;
    private GeoPoint geoPointLocation;
    private String hostID;
    private ArrayList<User> peopleAttending;



    //empty constructor for firestore
    public Event() {

    }

    public Event(byte[] imgBytes, String energyLevel, String weightRange,
                 String host, String date, String startTime, String endTime,
                 String location) {
        this.imgBytes = imgBytes;
        this.energyLevel = energyLevel;
        this.weightRange = weightRange;
        this.host = host;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
    }

    public Event(String id, byte[] imgBytes, String energyLevel, String weightRange, String host,
                 String date, String startTime, String endTime, String location, String description,
                 String triggers, GeoPoint geoPointLocation, String hostID) {
        this.id = id;
        this.imgBytes = imgBytes;
        this.energyLevel = energyLevel;
        this.weightRange = weightRange;
        this.host = host;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
        this.triggers = triggers;
        this.geoPointLocation = geoPointLocation;
        this.hostID = hostID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getImgBytes() {
        return imgBytes;
    }


    public String getEnergyLevel() {
        return energyLevel;
    }


    public String getWeightRange() {
        return weightRange;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDate() {
        return date;
    }


    public String getStartTime() {
        return startTime;
    }


    public String getEndTime() {
        return endTime;
    }


    public String getLocation() {
        return location;
    }

    //says is never used but is
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

    public GeoPoint getGeoPointLocation() {
        return geoPointLocation;
    }

    public void setGeoPointLocation(GeoPoint geoPointLocation) {
        this.geoPointLocation = geoPointLocation;
    }

    public ArrayList<User> getPeopleAttending() {
        return peopleAttending;
    }

    public void setPeopleAttending(ArrayList<User> peopleAttending) {
        this.peopleAttending = peopleAttending;
    }

    public String getHostID() {
        return hostID;
    }

    public void setHostID(String hostID) {
        this.hostID = hostID;
    }

    @Override
    public String toString() {
        return "Event{" +
                "host='" + host + '\'' +
                ", date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Arrays.equals(imgBytes, event.imgBytes) &&
                Objects.equals(energyLevel, event.energyLevel) &&
                Objects.equals(weightRange, event.weightRange) &&
                Objects.equals(host, event.host) &&
                Objects.equals(date, event.date) &&
                Objects.equals(startTime, event.startTime) &&
                Objects.equals(endTime, event.endTime) &&
                Objects.equals(location, event.location) &&
                Objects.equals(description, event.description) &&
                Objects.equals(triggers, event.triggers) &&
                Objects.equals(geoPointLocation, event.geoPointLocation) &&
                Objects.equals(hostID, event.hostID) &&
                Objects.equals(peopleAttending, event.peopleAttending);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, energyLevel, weightRange, host, date, startTime, endTime, location, description, triggers, geoPointLocation, hostID, peopleAttending);
        result = 31 * result + Arrays.hashCode(imgBytes);
        return result;
    }
}
