package com.model;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * DataWriter class that writes data to JSON files
 * Writes: Users, Songs, Posts
 * For users, writes: ID, Username, Email, Password, Type, FavoriteSongs, Friends, FavoritePosts
 * For songs, writes: ID, Genre, Title, Artist, Difficulty, SheetMusic, Tempo, TimeSignature, Measures, Chords, Notes
 * For posts, writes: ID, SongID, NumFavorites, Comments, AuthorID, Date, Private, Title, Body
 * @see DataConstants
 * @author Josiah White
 */
public class DataWriter extends DataConstants {
    /* 
     * Writes user data to JSON file
     */
    public static void saveUsers() {
        UserDatabase users = UserDatabase.getInstance();
        ArrayList<User> userList = users.getUsers();
        JSONArray jsonUsers = new JSONArray();
        // Iterates through each user and adds them to the JSON array
        for(int i = 0; i < userList.size(); i++) {
            jsonUsers.add(getUserJSON(userList.get(i)));
        }

        // Writes the JSON array to the file
        try(FileWriter file = new FileWriter(USER_FILE_NAME)) {
            file.write(jsonUsers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Goes through the given user and turns it to a JSONObject
     * @param user User to be turned into a JSONObject
     * @return JSONObject of the given User
     */
    private static JSONObject getUserJSON(User user) {
        JSONObject userDetails = new JSONObject();

        // Retrieves attributes and puts them into the JSONObject to be put into the file.  
        userDetails.put(USER_ID, user.getUUID().toString());
        userDetails.put(USER_USERNAME, user.getUsername());
        userDetails.put(USER_PASSWORD, user.getPassword());

        // TODO: Implement User having other objects as members and how to work through that properly.  
        //userDetails.put(USER_FAVORITE_SONGS, getUUIDList(user.getFavoriteSongs()));
        //userDetails.put(USER_FRIENDS, getUUIDList(user.getFriends()));
        //userDetails.put(USER_FAVORITE_POSTS, getUUIDList(user.getFavoritePosts()));
        
        return userDetails;
    }

    public static void savePlayers() {
        PlayerDatabase players = PlayerDatabase.getInstance();
        ArrayList<Player> playerList = players.getPlayers();
        JSONArray jsonPlayers = new JSONArray();

        // Iterates through each player and adds them to the JSON array
        for(int i = 0; i < playerList.size(); i++) {
            jsonPlayers.add(getPlayerJSON(playerList.get(i)));
        }

        // Writes the JSON array to the file
        try(FileWriter file = new FileWriter(PLAYER_FILE_NAME)) {
            file.write(jsonPlayers.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getPlayerJSON(Player player) {
        JSONObject playerDetails = new JSONObject();

        // Retrieves attributes and puts them into the JSONObject to be put into the file.
        playerDetails.put(PLAYER_NAME, player.getName());
        playerDetails.put(PLAYER_POSITION, player.getPosition());
        playerDetails.put(PLAYER_SCHOOL, player.getSchool());
        playerDetails.put(PLAYER_CONSENSUS_RANK, player.getConsensusRank());

        return playerDetails;
    }
    
    
    /*
     * Goes through the given list of items and gets their UUIDs
     * @param items List of items to get UUIDs from
     * @return JSONArray of UUIDs
     */
    private static <T> JSONArray getUUIDList(ArrayList<T> items) {
        ArrayList<String> uuidList = new ArrayList<>();
        try {
            for (T item : items) {
                Method getUUIDMethod = item.getClass().getMethod("getUUID");
                String uuid = ((UUID) getUUIDMethod.invoke(item)).toString();
                uuidList.add(uuid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray UUIDArray = new JSONArray();
        for(String uuid : uuidList) {
            UUIDArray.add(uuid);
        }
        return UUIDArray;
    }
}
