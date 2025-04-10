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
    
}
