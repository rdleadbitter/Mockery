package com.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MockDraft {
    private String draftName;
    private int year;
    private int maxRounds;
    private List<Pick> picks;
    private Integer score; // Nullable; only set after scoring
    private Set<Integer> draftedPlayerIds = new HashSet<>();
    private String userTeam;
    
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("ownerId")
    private UUID ownerId; // link back to User

    public MockDraft() {
        this.picks = new ArrayList<>();
        this.score = null;
        this.userTeam = "NONE";
        this.maxRounds = 7; // default
    }

    public MockDraft(String draftName, int year, List<Pick> picks, UUID ownerId, String userTeam) {
        this.draftName = draftName;
        this.year = year;
        this.maxRounds = 7; // default
        this.picks = picks;
        this.score = null;
        this.id = UUID.randomUUID();     // auto-generate unique ID
        this.ownerId = ownerId;          // track owning user
        this.userTeam = userTeam;
    }

    public MockDraft(String draftName, int year, int maxRounds, List<Pick> picks, UUID ownerId, String userTeam) {
        this.draftName = draftName;
        this.year = year;
        this.maxRounds = maxRounds;
        this.picks = picks;
        this.score = null;
        this.id = UUID.randomUUID();     // auto-generate unique ID
        this.ownerId = ownerId;          // track owning user
        this.userTeam = userTeam;
    }

    public String getDraftName() {
        return draftName;
    }

    public void setDraftName(String draftName) {
        this.draftName = draftName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Pick> getPicks() {
        return picks;
    }

    public void setPicks(List<Pick> picks) {
        this.picks = picks;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void addPick(Pick pick) {
        if (this.picks == null) {
            this.picks = new ArrayList<>();
        }
        this.picks.add(pick);
    }

    public Pick getPickByNumber(int pickNumber) {
        for (Pick pick : picks) {
            if (pick.getNumber() == pickNumber) {
                return pick;
            }
        }
        return null;
    }
    
    public boolean isPlayerTaken(int playerId) {
        return draftedPlayerIds.contains(playerId);
    }

    public void markPlayerTaken(int playerId) {
        draftedPlayerIds.add(playerId);
    }

    public Pick getNextUnfilledPick() {
        for (Pick pick : picks) {
            if (pick.getPlayer() == null && pick.getRound() <= maxRounds) {
                return pick;
            }
        }
        return null; // all picks are filled or maxRounds reached
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    @JsonIgnore
    public void setId(UUID id) {
        this.id = id;
    }

    @JsonIgnore
    public UUID getOwnerId() {
        return ownerId;
    }

    @JsonIgnore
    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "MockDraft{" +
                "draftName='" + draftName + '\'' +
                ", year=" + year +
                ", score=" + score +
                ", totalPicks=" + (picks != null ? picks.size() : 0) +
                '}';
    }

    public String getUserTeam() {
        return userTeam;
    }
    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }

    public int getMaxRounds() {
        return maxRounds;
    }
    public void setMaxRounds(int maxRounds) {
        this.maxRounds = maxRounds;
    }
}