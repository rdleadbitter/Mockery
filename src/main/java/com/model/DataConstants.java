package com.model;

public abstract class DataConstants {

    protected static final String USER_FILE_NAME = "data/Users.json";
    protected static final String USER_ID = "id";
    protected static final String USER_USERNAME = "username";
    protected static final String USER_PASSWORD = "password";
    protected static final String USER_MOCK_DRAFTS = "mockDrafts";

    protected static final String TEAM_FILE_NAME = "data/Teams.json";
    protected static final String TEAM_NAME = "name";
    protected static final String TEAM_CITY = "city";
    protected static final String TEAM_ABBREVIATION = "abbreviation";
    protected static final String TEAM_STANDING = "standing";
    protected static final String TEAM_PRIMARY_COLOR = "primaryColor";
    protected static final String TEAM_SECONDARY_COLOR = "secondaryColor";
    protected static final String TEAM_PICKS = "picks";
    protected static final String TEAM_NEEDS = "needs";

    protected static final String PLAYER_FILE_NAME = "data/Players.json";
    protected static final String PLAYER_NAME = "name";
    protected static final String PLAYER_POSITION = "position";
    protected static final String PLAYER_SCHOOL = "school";
    protected static final String PLAYER_CONSENSUS_RANK = "consensusRank";

    protected static final String PICK_FILE_NAME = "data/Picks.json";
    protected static final String PICK_TEAM = "team";
    protected static final String PICK_PLAYER = "player";
    protected static final String PICK_ROUND = "round";
    protected static final String PICK_NUMBER = "number";
    protected static final String PICK_TRADED = "traded";
    protected static final String PICK_TRADED_FROM = "tradedFrom";

    protected static final String MOCK_DRAFT_FILE_NAME = "data/MockDrafts.json";
    protected static final String MOCK_DRAFT_YEAR = "year";
    protected static final String MOCK_DRAFT_PICKS = "picks";
    protected static final String MOCK_DRAFT_SCORE = "score";
    protected static final String MOCK_DRAFT_ID = "id";
    protected static final String MOCK_DRAFT_OWNER_ID = "ownderId";
}
