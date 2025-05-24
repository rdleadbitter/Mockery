package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MockeryFacade {
    private static MockeryFacade instance;

    private final UserDatabase userDB;
    private final PlayerDatabase playerDB;
    private final TeamDatabase teamDB;
    private final PickDatabase pickDB;
    private final MockDraftDatabase draftDB;

    private User currentUser;
    private MockDraft currentDraft;
    private String userDraftTeamAbbr;

    private MockeryFacade() {
        System.out.println("Facade constructor starting");
        userDB = UserDatabase.getInstance();
        teamDB = TeamDatabase.getInstance();
        playerDB = PlayerDatabase.getInstance();
        pickDB = PickDatabase.getInstance();
        draftDB = MockDraftDatabase.getInstance();
        System.out.println("Facade constructor complete");
    }

    public static MockeryFacade getInstance() {
        if (instance == null) {
            instance = new MockeryFacade();
        }
        return instance;
    }

    public boolean login(String username, String password) {
        for (User user : userDB.getUsers()) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public boolean register(String username, String password) {
        if (userDB.getUserByUsername(username) != null) return false;
        User newUser = new User(username, password);
        userDB.addUser(newUser);
        DataWriter.saveUsers();
        currentUser = newUser;
        return true;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Player> getAllPlayers() {
        return playerDB.getPlayers();
    }

    public List<Team> getAllTeams() {
        return teamDB.getTeams();
    }

    public MockDraft createMockDraft(String name, int year, int maxRounds) {
        MockDraft draft = MockDraftBuilder.createMockDraft(name, year, maxRounds, currentUser);
        draft.setOwnerId(currentUser.getUUID());
        draft.setId(UUID.randomUUID());

        draftDB.addDraft(draft);                         // Save to draft store
        currentUser.addMockDraftId(draft.getId());       // Link in user
        DataWriter.saveUsers();                          // Persist updated user
        draftDB.save();// Persist all drafts
        return draft;
    }

    public boolean assignPlayerToPick(MockDraft draft, int pickNumber, int playerId) {
        Player player = playerDB.getPlayerById(playerId);
        boolean success = MockDraftBuilder.assignPlayerToPick(draft, pickNumber, playerId);
        if (success) draftDB.save();
        return success;
    }

    public boolean tradePick(MockDraft draft, int pickNumber, String newTeamAbbr) {
        Team newTeam = teamDB.getTeamByAbbreviation(newTeamAbbr);
        boolean success = MockDraftBuilder.tradePick(draft, pickNumber, newTeam);
        if (success) draftDB.save();
        return success;
    }

    public List<MockDraft> getUserDrafts() {
        if (currentUser == null) return new ArrayList<>();
        List<MockDraft> drafts = new ArrayList<>();
        for (UUID id : currentUser.getMockDrafts()) {
            MockDraft draft = draftDB.getDraftById(id);
            if (draft != null) drafts.add(draft);
        }
        return drafts;
    }

    public void logout() {
        currentUser = null;
    }

    public void setCurrentDraft(MockDraft draft) {
        this.currentDraft = draft;
    }

    public MockDraft getCurrentDraft() {
        return currentDraft;
    }

    public Team getTeamByAbbreviation(String abbr) {
        return teamDB.getTeamByAbbreviation(abbr);
    }

    public void setUserDraftTeam(String abbr) {
        this.userDraftTeamAbbr = abbr;
    }

    public String getUserDraftTeam() {
        return userDraftTeamAbbr;
    }
}
