package com.model;

public class Team {
    private String name;
    private String city;
    private String abbreviation;
    private int standing;
    private String primaryColor;
    private String secondaryColor;
    private int[] picks;
    private String[] needs;

    public Team() {
        this.name = "none";
        this.city = "none";
        this.abbreviation = "none";
        this.standing = 0;
        this.primaryColor = "#000000";
        this.secondaryColor = "#FFFFFF";

    }
    public Team(String name, String city, String abbreviation, int standing, String primaryColor, String secondaryColor, int[] picks, String[] needs) {
        this.name = name;
        this.city = city;
        this.abbreviation = abbreviation;
        this.standing = standing;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.picks = picks;
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
    public int[] getPicks() {
        return picks;
    }
    public void setPicks(int[] picks) {
        this.picks = picks;
    }
    public String[] getNeeds() {
        return needs;
    }
    public void setNeeds(String[] needs) {
        this.needs = needs;
    }
    public void addPick(int pick) {
        int[] newPicks = new int[picks.length + 1];
        System.arraycopy(picks, 0, newPicks, 0, picks.length);
        newPicks[picks.length] = pick;
        this.picks = newPicks;
    }
}
