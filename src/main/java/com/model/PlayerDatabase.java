package com.model;

import java.util.ArrayList;
import java.util.List;

public class PlayerDatabase {
    private static PlayerDatabase playerDatabase = null;
    private List<Player> players;

    private PlayerDatabase() {
        players = DataLoader.getPlayers();
    }

    public static PlayerDatabase getInstance() {
        if (playerDatabase == null) {
            playerDatabase = new PlayerDatabase();
        }
        return playerDatabase;
    }

    public List<Player> getPlayers() {
        return players;
    }
    
    public boolean addPlayer(Player player) {
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

    public boolean removePlayer(Player player) {
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

    public List<Player> sortByPosition(String position) {
        List<Player> sortedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getPosition().equalsIgnoreCase(position)) {
                sortedPlayers.add(player);
            }
        }
        return sortedPlayers;
    }

    public void save() {
        DataWriter.savePlayers();
    }

    public Player getPlayerById(int id) {
        for (Player player : players) {
            if (player.getConsensusRank() == id) {
                return player;
            }
        }
        return null;
    }
}
