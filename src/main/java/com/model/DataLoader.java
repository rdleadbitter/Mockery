package com.model;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class DataLoader extends DataConstants {

    public static ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONArray usersJSON = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : usersJSON) {
                JSONObject userJSON = (JSONObject) obj;
                UUID id = UUID.fromString((String) userJSON.get(USER_ID));
                String username = (String) userJSON.get(USER_USERNAME);
                String password = (String) userJSON.get(USER_PASSWORD);
                ArrayList<Draft> mockDrafts = new ArrayList<>();
                users.add(new User(id, username, password, mockDrafts));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static ArrayList<Player> getPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        try {
            FileReader reader = new FileReader(USER_FILE_NAME);
            JSONArray playersJSON = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : playersJSON) {
                JSONObject playerJSON = (JSONObject) obj;
                String name = (String) playerJSON.get(PLAYER_NAME);
                String position = (String) playerJSON.get(PLAYER_POSITION);
                String school = (String) playerJSON.get(PLAYER_SCHOOL);
                int consensusRank = ((Long) playerJSON.get(PLAYER_CONSENSUS_RANK)).intValue();
                players.add(new Player(name, position, school, consensusRank));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }

    public static ArrayList<Pick> getPicks() {
        ArrayList<Pick> picks = new ArrayList<>();
        try {
            FileReader reader = new FileReader(PICK_FILE_NAME);
            JSONArray picksJSON = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : picksJSON) {
                JSONObject pickJSON = (JSONObject) obj;
                int number = (int) pickJSON.get(PICK_NUMBER);
                int round = (int) pickJSON.get(PICK_ROUND);
                Team team = (Team) pickJSON.get(PICK_TEAM);
                boolean traded = (boolean) pickJSON.get(PICK_TRADED);
                picks.add(new Pick(team, number, round, traded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return picks;
    }

    public static ArrayList<Team> getTeams() {
        ArrayList<Team> teams = new ArrayList<>();
        try {
            FileReader reader = new FileReader(TEAM_FILE_NAME);
            JSONArray teamsJSON = (JSONArray) new JSONParser().parse(reader);
            for (Object obj : teamsJSON) {
                JSONObject teamJSON = (JSONObject) obj;
                String name = (String) teamJSON.get(TEAM_NAME);
                String city = (String) teamJSON.get(TEAM_CITY);
                String abbreviation = (String) teamJSON.get(TEAM_ABBREVIATION);
                int standing = ((Long) teamJSON.get(TEAM_STANDING)).intValue();
                String primaryColor = (String) teamJSON.get(TEAM_PRIMARY_COLOR);
                String secondaryColor = (String) teamJSON.get(TEAM_SECONDARY_COLOR);
                ArrayList<Pick> picks = new ArrayList<>();
                ArrayList<String> needs = new ArrayList<>();
                JSONArray needsJSON = (JSONArray) teamJSON.get(TEAM_NEEDS);
                for (Object need : needsJSON) {
                    needs.add((String) need);
                }
                JSONArray picksJSON = (JSONArray) teamJSON.get(TEAM_PICKS);
                for (Object obj2 : picksJSON) {
                    JSONObject pickJSON = (JSONObject) obj2;
                    int number = ((Long) pickJSON.get(PICK_NUMBER)).intValue();
                    int round = ((Long) pickJSON.get(PICK_ROUND)).intValue();
                    Team team = (Team) pickJSON.get(PICK_TEAM);
                    boolean traded = (boolean) pickJSON.get(PICK_TRADED);
                    picks.add(new Pick(team, number, round, traded));
                }
                teams.add(new Team(name, city, abbreviation, standing, primaryColor, secondaryColor, picks, needs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teams;
    }
    
}
