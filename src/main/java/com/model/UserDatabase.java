package com.model;

import java.util.ArrayList;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class UserDatabase {
    private static UserDatabase userDatabase;
    private static ArrayList<User> users;

    /**
     * Constructor for the UserDatabase class
     * Initializes the users ArrayList by calling the DataLoader class
     */
    private UserDatabase() {
        users = DataLoader.getUsers();
    }

    /**
     * Singleton method to get the instance of the UserDatabase
     * If the instance does not exist, it creates a new instance
     * @return the instance of the UserDatabase
     */
    public static UserDatabase getInstance() {
        if (userDatabase == null) {
            userDatabase = new UserDatabase();
        }
        return userDatabase;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Method to get a user by their email and password
     * @return the user if the user exists, null if the user does not exist
     */
    public User getUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Method to add a user to the UserDatabase
     * @return false if the user already exists, true if the user is added to the UserDatabase
     */
    public static boolean addUser(User user) {
        if (user == null) {
            return false;
        } 
        for (User existingUser : users) {
            if (user.getUsername().equals(existingUser.getUsername())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public void save() {
        DataWriter.saveUsers();
    }
}
