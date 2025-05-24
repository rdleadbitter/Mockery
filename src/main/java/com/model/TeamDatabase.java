package com.model;

import java.util.List;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class TeamDatabase {
    private static TeamDatabase teamDatabase = null;
    private static List<Team> teams;

    /**
     * Constructor for the PickDatabase class
     * Initializes the picks ArrayList by calling the DataLoader class
     */
    private TeamDatabase() {
        teams = DataLoader.getTeams();
    }

    /**
     * Singleton method to get the instance of the UserDatabase
     * If the instance does not exist, it creates a new instance
     * @return the instance of the UserDatabase
     */
    public static TeamDatabase getInstance() {
        if (teamDatabase == null) {
            teamDatabase = new TeamDatabase();
        }
        return teamDatabase;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Team getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public Team getTeamByAbbreviation(String abbreviation) {
        for (Team team : teams) {
            if (team.getAbbreviation().equalsIgnoreCase(abbreviation)) {
                return team;
            }
        }
        return null;
    }

    public void save() {
        DataWriter.saveTeams();
    }
}
