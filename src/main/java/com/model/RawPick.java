package com.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class RawPick {

    private String teamAbbreviation;
    private int pickNumber;
    private int round;
    private boolean traded;

    public RawPick() {
        // Required for Jackson deserialization
    }

    public RawPick(String teamAbbreviation, int pickNumber, int round, boolean traded) {
        this.teamAbbreviation = teamAbbreviation;
        this.pickNumber = pickNumber;
        this.round = round;
        this.traded = traded;
    }

    public String getTeamAbbreviation() {
        return teamAbbreviation;
    }

    public void setTeamAbbreviation(String teamAbbreviation) {
        this.teamAbbreviation = teamAbbreviation;
    }

    public int getNumber() {
        return pickNumber;
    }

    public void setNumber(int pickNumber) {
        this.pickNumber = pickNumber;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isTraded() {
        return traded;
    }

    public void setTraded(boolean traded) {
        this.traded = traded;
    }
}
