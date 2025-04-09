package com.model;

import java.util.ArrayList;

public class Draft {
    private ArrayList<Pick> picks;

    public Draft() {
        // Default constructor
        this.picks = new ArrayList<>();
    }

    public Draft(ArrayList<Pick> picks) {
        // Constructor with picks parameter
        this.picks = picks;
    }
    public ArrayList<Pick> getPicks() {
        // Getter for picks
        return picks;
    }

    public void draftPlayer(Player player, int index) {
        // Method to draft a player for a team
        if (index < 0 || index >= picks.size()) {
            System.out.println("Invalid index. Cannot draft player.");
            return;
        }
        Pick pick = picks.get(index);
        pick.setPlayer(player);
    }

    public void tradePick(int index1, int index2) {
        // Method to trade picks between two teams
        if (index1 < 0 || index1 >= picks.size() || index2 < 0 || index2 >= picks.size()) {
            System.out.println("Invalid index. Cannot trade picks.");
            return;
        }
        Pick pick1 = picks.get(index1);
        Pick pick2 = picks.get(index2);

        // Swap the picks
        Pick temp = pick1;
        picks.set(index1, pick2);
        picks.set(index2, temp);
    }
}
