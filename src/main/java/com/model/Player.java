package com.model;

public class Player 
{
	private String name;
	private String position;
	private String school;
    private int consensusRank;
	
	public Player()
	{
		this.name = "none";
		this.position = "none";
		this.school = "none";
	}
	public Player(String n, String p, String s, int r)
	{
		this.name = n;
		this.position = p;
		this.school = s;
        this.consensusRank = r;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
    public int getConsensusRank() {
        return consensusRank;
    }
    public void setConsensusRank(int consensusRank) {
        this.consensusRank = consensusRank;
    }
}