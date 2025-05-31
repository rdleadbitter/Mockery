package com.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The UserDatabase class is a singleton class that holds all the users in the system
 * It is used to get a user by their email and password and to add a user to the system
 * It is also used to get all the users in the system
 */
public class UserDatabase {
    private static UserDatabase userDatabase;
    private List<User> users;
    private Map<String, User> userMap;
    private User currentUser;

    /**
     * Constructor for the UserDatabase class
     * Initializes the users ArrayList by calling the DataLoader class
     */
    private UserDatabase() {
        users = DataLoader.getUsers();
        System.out.println("Users loaded into UserDatabase: " + users.size());
        for (User u : users) {
            System.out.println("🧑 " + u.getUsername() + " / " + u.getPassword());
        }
        userMap = new HashMap<>();
        for (User user : users) {
            userMap.put(user.getUsername(), user);
        }
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

    public List<User> getUsers() {
        return users;
    }

    /**
     * Method to get a user by their email and password
     * @return the user if the user exists, null if the user does not exist
     */
    public User getUserByUsername(String username) {
        return userMap.get(username);
    }

    /**
     * Method to add a user to the UserDatabase
     * @return false if the user already exists, true if the user is added to the UserDatabase
     */
    public boolean addUser(User user) {
        if (user == null) return false;
        if (userMap.containsKey(user.getUsername())) return false;

        users.add(user);
        userMap.put(user.getUsername(), user);

        save();
        return true;
    }


    public void save() {
        DataWriter.saveUsers();
    }

    public boolean authenticate(String username, String password) {
        User user = userMap.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public void logout() {
        this.currentUser = null;
    }

    public void removeDraftFromUsers(UUID id) {
        for (User user : users) {
            if (user.getMockDrafts().contains(id)) {
                user.getMockDrafts().remove(id);
            }
        }
        save();
    }
}
