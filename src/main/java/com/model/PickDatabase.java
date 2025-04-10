package com.model;

import java.util.ArrayList;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class PickDatabase {
    private static PickDatabase pickDatabase;
    private static ArrayList<Pick> picks;

    /**
     * Constructor for the PickDatabase class
     * Initializes the picks ArrayList by calling the DataLoader class
     */
    private PickDatabase() {
        picks = DataLoader.getPicks();
    }

    /**
     * Singleton method to get the instance of the UserDatabase
     * If the instance does not exist, it creates a new instance
     * @return the instance of the UserDatabase
     */
    public static PickDatabase getInstance() {
        if (pickDatabase == null) {
            pickDatabase = new PickDatabase();
        }
        return pickDatabase;
    }

    public ArrayList<Pick> getPicks() {
        return picks;
    }

    /**
     * Method to get a user by their email and password
     * @return the user if the user exists, null if the user does not exist
     */
    public Pick getPick(int number) {
        for (Pick pick : picks) {
            if (pick.getNumber() == number) {
                return pick;
            }
        }
        return null;
    }

    public ArrayList<Pick> getPicksByRound(int round) {
        ArrayList<Pick> picksInRound = new ArrayList<>();
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
