package controller;

import java.util.Objects;

public class Team {
	private League league ; // Must contain the division name or division id 
	private Player man ; 
	private Player woman ; 
	private String teamName ; 
	private int teamId ;
	private String divisionName ; 
	
	public Team(League league, Player man, Player woman, String teamName, int teamId, String divisionName) {
		this.league = league ; 
		this.man = man ; 
		this.woman = woman ; 
		this.teamName = teamName ; 
		this.teamId = teamId ; 
		this.divisionName = divisionName ; 
	}
	
	public Team(League league, Player man, Player woman, String teamName) {
		this.league = league ; 
		this.man = man ; 
		this.woman = woman ; 
		this.teamName = teamName ; 
	}
	
	public League getLeague() {
		return league;
	}
	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public void setLeague(League league) {
		this.league = league;
	}
	public Player getMan() {
		return man;
	}
	public void setMan(Player man) {
		this.man = man;
	}
	public Player getWoman() {
		return woman;
	}
	public void setWoman(Player woman) {
		this.woman = woman;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	@Override
	public int hashCode() {
		return Objects.hash(league, man, woman);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		return Objects.equals(league, other.league) && Objects.equals(man, other.man)
				&& Objects.equals(woman, other.woman);
	} 
	
	public String toString() {
		String divisionName = league.getDivisionName() ; 
		if(divisionName == null || divisionName.isEmpty()) {
			divisionName = "brown" ; 
		}
		String string = teamName + " " + league.getDayOfWeek() + " " + league.getYear() + " " + divisionName ; 
		string += " " + woman.getFirstName() + " " + woman.getLastName() + ", " + man.getFirstName() + " " + man.getLastName() + " " + teamId ;
		return string ; 
	}
	
}
