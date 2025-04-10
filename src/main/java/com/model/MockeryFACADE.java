package com.model;

public class MockeryFACADE {
    private User user;
    private UserDatabase users;
    private PickDatabase picks;
    private TeamDatabase teams;
    private PlayerDatabase players;

    public MockeryFACADE() {
        users = UserDatabase.getInstance();
        picks = PickDatabase.getInstance();
        teams = TeamDatabase.getInstance();
        players = PlayerDatabase.getInstance();
    }

    public void setFacadeUser(User user) {
        this.user = user;
    }

    /**
     * Determines if the user is logged in
     * @params email and password of the user
     * @return true if the user is logged in, false if the user is not logged in or the user does not exist
     */
    public boolean login(String username, String password) {
        user = users.getUser(username, password);
        if (user == null) {
            return false;
        }
        return true;
    }

    /**
     * Method to create a new user and add them to the UserDatabase
     * @params username, password, and email of the user
     * @return false if the user already exists, true if the user is created and added to the UserDatabase
     */
    public boolean makeUser(String username, String password) {
        User newUser = User.getInstance(username, password);
        System.out.println(newUser);
        if(newUser == null) {
            return false;
        }
        user = newUser;
        return UserDatabase.addUser(newUser);
    }

    public void logout() {
        user = null;
        DataWriter.saveUsers();
    }
}
