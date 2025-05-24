package com.model;

public class Pick {
    private Player player;
    private String team;
    private boolean traded;
    private int number;
    private int round;

    public Pick() {
        this.player = new Player();
        this.team = "";
        traded = false;
        number = 0;
        round = 0;
    }
    public Pick(String team, int number, int round, boolean traded) {
        this.team = team;
        this.traded = traded;
        this.number = number;
        this.round = round;
    }
    public Pick(String team, int number, int round, boolean traded, Player player) {
        this.team = team;
        this.traded = traded;
        this.number = number;
        this.round = round;
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    } 
    public void setPlayer(Player player) {
        this.player = player;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }
    public boolean isTraded() {
        return traded;
    }
    public void setTraded(boolean traded) {
        this.traded = traded;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public int getRound() {
        return round;
    }
    public void setRound(int round) {
        this.round = round;
    }
}
