package com.model;

import java.util.ArrayList;

public class PlayerDatabase {
    private static PlayerDatabase playerDatabase;
    private static ArrayList<Player> players;

    private PlayerDatabase() {
        players = DataLoader.getPlayers();
    }

    public static PlayerDatabase getInstance() {
        if (playerDatabase == null) {
            playerDatabase = new PlayerDatabase();
        }
        return playerDatabase;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    
    public static boolean addPlayer(Player player) {
        if (player == null) {
            return false;
        } 
        for (Player existingPlayer : players) {
            if (player.getName().equals(existingPlayer.getName())) {
                return false;
            }
        }
        players.add(player);
        return true;
    }

    public static boolean removePlayer(Player player) {
        if (player == null) {
            return false;
        } 
        for (Player existingPlayer : players) {
            if (player.getName().equals(existingPlayer.getName())) {
                players.remove(existingPlayer);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Player> sortByPosition(String position) {
        ArrayList<Player> sortedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                sortedPlayers.add(player);
            }
        }
        return sortedPlayers;
    }
}
