package controller;
/**
 * This class is to put the data into the table view for the league standings. 
 * @author thom
 *
 */
public class TeamStandings implements Comparable {
	int rank ; 
	String manName ; 
	String womanName ; 
	int points ; 
	String currentDivision ;
	String teamName ;
	
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public TeamStandings(String man, String woman, String divisionName) {
		this.manName = man ; 
		this.womanName = woman ; 
		this.currentDivision = divisionName ;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getManName() {
		return manName;
	}
	public void setManName(String manName) {
		this.manName = manName;
	}
	public String getWomanName() {
		return womanName;
	}
	public void setWomanName(String womanName) {
		this.womanName = womanName;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getCurrentDivision() {
		return currentDivision;
	}
	public void setCurrentDivision(String currentDivision) {
		this.currentDivision = currentDivision;
	}
	public void addPoints(int amount) {
		this.points += amount ; 
	}
	
	// This will sort highest to lowest.
	@Override
	public int compareTo(Object o) {
		int compare = 0 ;
		TeamStandings other = (TeamStandings)o ; 
		if(other.points > this.points) {
			compare = 1 ; 
		}
		if(other.points < this.points) {
			compare = -1 ; 
		}
		return compare ;
	} 
	
	
	
	
}
