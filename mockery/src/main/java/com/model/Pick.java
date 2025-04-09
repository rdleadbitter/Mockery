package com.model;

public class Pick {
    private Player player;
    private Team team;
    private boolean traded;
    private int number;
    private int round;

    public Pick() {
        this.player = new Player();
        this.team = new Team();
        traded = false;
        number = 0;
        round = 0;
    }
    public Pick(Team team, int number, int round, boolean traded) {
        this.team = team;
        this.traded = traded;
        this.number = number;
        this.round = round;
    }
    public Player getPlayer() {
        return player;
    } 
    public void setPlayer(Player player) {
        this.player = player;
    }
    public Team getTeam() {
        return team;
    }
    public void setTeam(Team team) {
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
