package com.model;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    private String username;
    private String password;
    private ArrayList<Draft> mockDrafts;
    private UUID id;

    public User() {
        this.username = "none";
        this.password = "none";
        this.mockDrafts = new ArrayList<>();
    }

    /**
     * Default constructor for User
     * Initializes the id, username, password, email, favorite songs, friends, and favorite posts
     * @param id the id of the user
     * @param username the username of the user
     * @param password the password of the user
     */
    public User(UUID id, String username, String password, ArrayList<Draft> mockDrafts) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.mockDrafts = mockDrafts;
    }

    /**
     * Constructor for User in the case that the user is not already in the database and needs to be created
     * Initializes the id, username, password, email, and mock drafts
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.mockDrafts = new ArrayList<>();
        this.id = UUIDgenerator(); // Generate a unique ID for the user
    }

    /**
     * Static method to get an instance of a user
     * Checks if the user already exists in the database
     * If the user does not exist, creates a new user
     * @return the user if the user does not already exist, null if the user already exists
     */
    public static User getInstance(String username, String password) {
        System.out.println("Creating user");
        for (User user : UserDatabase.getInstance().getUsers()) {
            if (user.isMatch(username, password)) {
                return null;
            } else if (user.getUsername().equals(username)) {
                return null;
            }
        }
        return new User(username, password);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUUID() {
        return id;
    }

    public void setUUID(UUID id) {
        this.id = id;
    }

    public ArrayList<Draft> getMockDrafts() {
        return mockDrafts;
    }

    public Draft getMockDraft(int index) {
        if (index < 0 || index >= mockDrafts.size()) {
            return null; // or throw an exception
        }
        return mockDrafts.get(index);
    }

    public void setMockDrafts(ArrayList<Draft> mockDrafts) {
        this.mockDrafts = mockDrafts;
    }

    public void addMockDraft(Draft draft) {
        this.mockDrafts.add(draft);
    }

    public void removeMockDraft(Draft draft) {
        this.mockDrafts.remove(draft);
    }

    public void clearMockDrafts() {
        this.mockDrafts.clear();
    }

    public UUID UUIDgenerator() {
        return UUID.randomUUID();
    }

    public boolean isMatch(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}
