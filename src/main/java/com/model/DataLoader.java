package com.model;

import java.io.File;
import java.io.FileReader;
import java.util.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataLoader extends DataConstants {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try {
            List<Map<String, Object>> userList = mapper.readValue(
                new File(USER_FILE_NAME),
                new TypeReference<List<Map<String, Object>>>() {}
            );

            for (Map<String, Object> userMap : userList) {
                UUID id = UUID.fromString((String) userMap.get(USER_ID));
                String username = (String) userMap.get(USER_USERNAME);
                String password = (String) userMap.get(USER_PASSWORD);

                List<UUID> draftIds = new ArrayList<>();
                List<String> ids = (List<String>) userMap.get(USER_MOCK_DRAFTS);
                if (ids != null) {
                    for (String s : ids) {
                        draftIds.add(UUID.fromString(s));
                    }
                }

                User user = new User(id, username, password, draftIds);
                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<MockDraft> getMockDrafts() {
        try {
            File file = new File(MOCK_DRAFT_FILE_NAME);
            System.out.println("📂 Loading drafts from: " + file.getAbsolutePath());
            List<MockDraft> loaded = mapper.readValue(file, new TypeReference<List<MockDraft>>() {});
            System.out.println("✅ Loaded " + loaded.size() + " mock drafts.");
            return loaded;
        } catch (Exception e) {
            System.out.println("❌ Failed to load mock drafts: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Player> getPlayers() {
        try {
            return mapper.readValue(new File(PLAYER_FILE_NAME), new TypeReference<List<Player>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Team> getTeams() {
        try {
            return mapper.readValue(new File(TEAM_FILE_NAME), new TypeReference<List<Team>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static List<Pick> getPicks(Map<String, Team> teamMap) {
        List<Pick> picks = new ArrayList<>();
        try {
            List<RawPick> rawPicks = mapper.readValue(
                new File(PICK_FILE_NAME),
                new TypeReference<List<RawPick>>() {}
            );

            for (RawPick raw : rawPicks) {
                String abbr = raw.getTeamAbbreviation();
                if (abbr != null) {
                    picks.add(new Pick(abbr, raw.getNumber(), raw.getRound(), raw.isTraded()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picks;
    }

    public static Map<String, Team> buildTeamMap(List<Team> teams) {
        Map<String, Team> teamMap = new HashMap<>();
        for (Team t : teams) {
            teamMap.put(t.getAbbreviation(), t);
        }
        return teamMap;
    }
}
