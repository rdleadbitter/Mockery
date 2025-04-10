package com.model;

import java.util.ArrayList;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class TeamDatabase {
    private static TeamDatabase teamDatabase;
    private static ArrayList<Team> teams;

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

    public ArrayList<Team> getTeams() {
        return teams;
    }

    /**
     * Method to get a user by their email and password
     * @return the user if the user exists, null if the user does not exist
     */
    public Team getTeam(String name) {
        for (Team team : teams) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }
        return null;
    }

    public void save() {
        DataWriter.saveTeams();
    }
}
