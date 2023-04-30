package controller;

public class Seed {
	int teamId ; 
	int bracketId ; 
	int seed ; 
	int seedId ;
	
	public Seed() {
		this.teamId = -1 ; 
		this.bracketId = -1 ; 
		this.seedId = -1 ; 
		this.seed = -1 ; 
	}
	
	public Seed(int teamId, int bracketId, int seed) {
		this.teamId = teamId ; 
		this.bracketId = bracketId ; 
		this.seedId = -1 ; 
		this.seed = seed ; 
	}
	
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getBracketId() {
		return bracketId;
	}
	public void setBracketId(int bracketId) {
		this.bracketId = bracketId;
	}
	public int getSeed() {
		return seed;
	}
	public void setSeed(int seed) {
		this.seed = seed;
	}
	public int getSeedId() {
		return seedId;
	}
	public void setSeedId(int seedId) {
		this.seedId = seedId;
	}
	
	public String toString() {
		return "seedId: " + this.seedId + ", seed Value " + this.seed + ", teamId: " + this.teamId ;
	}
}
