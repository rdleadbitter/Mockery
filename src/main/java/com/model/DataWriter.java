package com.model;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DataWriter extends DataConstants {

    private static final ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    public static void saveUsers() {
        UserDatabase users = UserDatabase.getInstance();
        try {
            mapper.writeValue(new File(USER_FILE_NAME), users.getUsers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveMockDrafts(List<MockDraft> drafts) {
        try {
            mapper.writeValue(new File(MOCK_DRAFT_FILE_NAME), drafts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayers() {
        PlayerDatabase players = PlayerDatabase.getInstance();
        try {
            mapper.writeValue(new File(PLAYER_FILE_NAME), players.getPlayers());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveTeams() {
        TeamDatabase teams = TeamDatabase.getInstance();
        try {
            mapper.writeValue(new File(TEAM_FILE_NAME), teams.getTeams());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void savePicks() {
        PickDatabase picks = PickDatabase.getInstance();
        try {
            mapper.writeValue(new File(PICK_FILE_NAME), picks.getPicks());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
