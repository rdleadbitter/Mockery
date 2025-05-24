package com.model;

import java.util.ArrayList;

public class Team {
    private String name;
    private String city;
    private String abbreviation;
    private int standing;
    private String primaryColor;
    private String secondaryColor;
    private ArrayList<String> needs;

    public Team() {
        this.name = "none";
        this.city = "none";
        this.abbreviation = "none";
        this.standing = 0;
        this.primaryColor = "#000000";
        this.secondaryColor = "#FFFFFF";

    }
    public Team(String name, String city, String abbreviation, int standing, String primaryColor, String secondaryColor, ArrayList<Pick> picks, ArrayList<String> needs) {
        this.name = name;
        this.city = city;
        this.abbreviation = abbreviation;
        this.standing = standing;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.needs = needs;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getAbbreviation() {
        return abbreviation;
    }
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
    public int getStanding() {
        return standing;
    }
    public void setStanding(int standing) {
        this.standing = standing;
    }
    public String getPrimaryColor() {
        return primaryColor;
    }
    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }
    public String getSecondaryColor() {
        return secondaryColor;
    }
    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
    public ArrayList<String> getNeeds() {
        return needs;
    }
    public void setNeeds(ArrayList<String> needs) {
        this.needs = needs;
    }
}
