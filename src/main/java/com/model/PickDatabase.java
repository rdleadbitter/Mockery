package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class PickDatabase {

    static {
    System.out.println("PickDatabase class LOADED");
}

    private static PickDatabase pickDatabase = null;
    private List<Pick> picks;

    /**
     * Constructor for the PickDatabase class
     * Initializes the picks List by calling the DataLoader class
     */
    private PickDatabase() {
        System.out.println("[PickDatabase] Initializing...");

        List<Team> teams = TeamDatabase.getInstance().getTeams();
        System.out.println("[PickDatabase] Loaded " + teams.size() + " teams");

        Map<String, Team> teamMap = DataLoader.buildTeamMap(teams);
        System.out.println("[PickDatabase] Team map keys: " + teamMap.keySet());

        this.picks = DataLoader.getPicks(teamMap);
        System.out.println("[PickDatabase] Loaded " + picks.size() + " picks");
    }

    public static PickDatabase getInstance() {
        if (pickDatabase == null) {
            pickDatabase = new PickDatabase();
        }
        return pickDatabase;
    }

    public List<Pick> getPicks() {
        return picks;
    }

    public Pick getPick(int number) {
        for (Pick pick : picks) {
            if (pick.getNumber() == number) {
                return pick;
            }
        }
        return null;
    }

    public List<Pick> getPicksByRound(int round) {
        List<Pick> picksInRound = new ArrayList<>();
        for (Pick pick : picks) {
            if (pick.getRound() <= round) {
                picksInRound.add(pick);
            }
        }
        return picksInRound;
    }

    public void save() {
        DataWriter.savePicks();
    }
}
