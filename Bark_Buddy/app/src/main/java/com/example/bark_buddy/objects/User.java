package com.example.bark_buddy.objects;

import java.util.ArrayList;

public class User {

    private String name;
    private final String email;
    private final String password;
    private String description;
    private String weightRange;
    private String energyLevel;
    private String dogName;
    private String dogBreed;
    private String triggers;
    private ArrayList<String> friends;
    private String userID;
    private byte [] vacReport;
    private byte [] profilePicture;
    private final ArrayList<String> activeMessges;



    public User( String email, String password) {
        this.email = email;
        this.password = password;
        activeMessges = new ArrayList<>();
    }

    public User(String name, String email, String password, String description, String weightRange,
                String energyLevel, String dogName, String dogBreed, String triggers,
                ArrayList<String> friends, String userID, byte[] vacReport, byte[] profilePicture) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.weightRange = weightRange;
        this.energyLevel = energyLevel;
        this.dogName = dogName;
        this.dogBreed = dogBreed;
        this.triggers = triggers;
        this.friends = friends;
        this.userID = userID;
        this.vacReport = vacReport;
        this.profilePicture = profilePicture;
        activeMessges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeightRange() {
        return weightRange;
    }

    public void setWeightRange(String weightRange) {
        this.weightRange = weightRange;
    }

    public String getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(String energyLevel) {
        this.energyLevel = energyLevel;
    }

    public String getDogName() {
        return dogName;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public String getTriggers() {
        return triggers;
    }

    public void setTriggers(String triggers) {
        this.triggers = triggers;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public byte[] getVacReport() {
        return vacReport;
    }

    public void setVacReport(byte[] vacReport) {
        this.vacReport = vacReport;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public ArrayList<String> getActiveMessges() {
        return activeMessges;
    }


}
