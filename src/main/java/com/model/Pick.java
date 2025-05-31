package com.model;

public class Pick {
    private Player player;
    private String team;
    private boolean traded;
    private int number;
    private int round;
    private String tradedFrom;

    public Pick() {
        this.player = new Player();
        this.team = "";
        traded = false;
        number = 0;
        round = 0;
        tradedFrom = "";
    }
    public Pick(String team, int number, int round, boolean traded, String tradedFrom) {
        this.team = team;
        this.traded = traded;
        this.number = number;
        this.round = round;
        this.tradedFrom = tradedFrom;
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
    public String getTradedFrom() {
        return tradedFrom;
    }
    public void setTradedFrom(String tradedFrom) {
        this.tradedFrom = tradedFrom;
    }
}
