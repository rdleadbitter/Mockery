package com.model;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String username;
    private String password;
    private List<UUID> mockDrafts;

    @JsonProperty("id")
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
    public User(UUID id, String username, String password, List<UUID> mockDrafts) {
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

    @JsonIgnore
    public UUID getUUID() {
        return id;
    }

    public void setUUID(UUID id) {
        this.id = id;
    }

    public List<UUID> getMockDrafts() {
        return mockDrafts;
    }

    public UUID getMockDraft(UUID id) {
        if (id == null) {
            return null; // or throw an exception
        }
        for (UUID draft : mockDrafts) {
            if (draft.equals(id)) {
                return draft;
            }
        }
        return null;
    }

    public void setMockDrafts(List<UUID> mockDrafts) {
        this.mockDrafts = mockDrafts;
    }

    public void addMockDraft(UUID draft) {
        this.mockDrafts.add(draft);
    }

    public void removeMockDraft(MockDraft draft) {
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

    public void addMockDraftId(UUID id) {
        if (id != null && !mockDrafts.contains(id)) {
            mockDrafts.add(id);
        }
    }
}
