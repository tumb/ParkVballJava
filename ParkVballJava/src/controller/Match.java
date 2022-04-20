package controller;

import java.util.Objects;

public class Match {

	private String date ; 
	private String teamAName ;
	private String teamBName ;
	private int teamAWins ; 
	private int teamBWins ;
	private String divisionName ; // This is the division when the match was played. May not be the current division. 
	private League league ;
	private int id ; // Might be handy for listeners to use. Using the one from the database
	public int getDivisionValue() {
		return divisionValue;
	}

	public void setDivisionValue(int divisionValue) {
		this.divisionValue = divisionValue;
	}

	private int divisionValue ; // so that it doesn't have to be looked up for each match during standings computations.

	// This will be for a new match that's not yet in the database.
	public Match(String teamA, String teamB, String matchDate, String division) {
		this.teamAName = teamA ; 
		this.teamBName = teamB ;
		this.date = matchDate ;
		this.divisionName = division ; 
	}
	
	public Match(League league, String teamA, String teamB, String matchDate, String division) {
		this.teamAName = teamA ; 
		this.teamBName = teamB ;
		this.date = matchDate ;
		this.divisionName = division ; 
		this.league = league ; 
	}
	
	public Match(String teamA, String teamB, String matchDate, String divisionName, int scheduleId) {
		this.teamAName = teamA ; 
		this.teamBName = teamB ;
		this.date = matchDate ;
		this.divisionName = divisionName ; 
		this.id = scheduleId ; 
	}
	
	public Match(League league, String teamA, int teamAwins, String teamB, int teamBwins, String matchDate, String division, int id) {
		this.teamAName = teamA ; 
		this.teamBName = teamB ;
		this.date = matchDate ;
		this.divisionName = division ; 
		this.league = league ; 
		this.teamAWins = teamAwins ; 
		this.teamBWins = teamBwins ; 
		this.id = id ; 
	}
	
	public Match(String displayMatchString) {
		// This is the inverse of toString() turning the string back into a match.
		int endOfTeamA = displayMatchString.indexOf(" v ") ; 
		int endOfTeamB = displayMatchString.indexOf(" in ") ;
		int endOfDivison = displayMatchString.indexOf(" on ") ;
		int endOfDate = displayMatchString.indexOf(" id: "	) ;
		if(endOfDate < 0) {
			endOfDate = displayMatchString.length() ; 
		}
		if(isParseableDisplayString(endOfTeamA, endOfTeamB, endOfDivison, endOfDate, displayMatchString.length())) {
			this.teamAName = displayMatchString.substring(0, endOfTeamA).trim() ;  
			this.teamBName = displayMatchString.substring(endOfTeamA + 3, endOfTeamB).trim() ; // 3 is length of " v "   
			this.divisionName = displayMatchString.substring(endOfTeamB + 4, endOfDivison).trim() ; // 3 is length of " v "
			this.date = displayMatchString.substring(endOfDivison + 4, endOfDate).trim() ;
			if(endOfDate < displayMatchString.length()) {
				this.id = Integer.parseInt(displayMatchString.substring(endOfDate + 5, displayMatchString.length()).trim()) ;
			}
		}
	}
	
	public Match(League league, String teamA, int winsA, String teamB, int winsB, int divisionValue) {
		this.teamAName = teamA ; 
		this.teamBName = teamB ; 
		this.teamAWins = winsA ;
		this.teamBWins = winsB ; 
		this.divisionValue = divisionValue ; 
	}

	public Match(String teamA, int aWins, String teamB, int bWins, String matchDate, String divisionName,
			int id2) {
		this.teamAName = teamA ; 
		this.teamAWins = aWins ;
		this.teamBName = teamB ; 
		this.teamBWins = bWins ; 
		this.date = matchDate ; 
		this.divisionName = divisionName ; 
		this.id = id2 ;
	}

	/**
	 * This is minimum for a match to be valid. 
	 * To be fully useful it will need a league and a division or division name.
	 * During the scheduling process this may be all that's available. 
	 * @return
	 */
	public boolean isValidMatch() {
		boolean isValid = this.teamAName != null && ! this.teamAName.isEmpty() ; 
		isValid = isValid && this.teamBName != null && ! this.teamBName.isEmpty() ;
		isValid = isValid && this.date != null && ! this.date.isEmpty() ;
		return isValid ; 
	}
	
	private boolean isParseableDisplayString(int endOfTeamA, int endOfTeamB, int endOfDivison, int endOfDate, int length) {
		boolean isParseable = 0 < endOfTeamA ;
		isParseable = isParseable && endOfTeamA < endOfTeamB ; 
		isParseable = isParseable && endOfTeamB < endOfDivison ; 
		isParseable = isParseable && endOfDivison < endOfDate ; 
		isParseable = isParseable && endOfDate <= length ; 
		return isParseable ;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTeamAName() {
		return teamAName;
	}
	public void setTeamAName(String teamAName) {
		this.teamAName = teamAName;
	}
	public String getTeamBName() {
		return teamBName;
	}
	public void setTeamBName(String teamBName) {
		this.teamBName = teamBName;
	}
	public int getTeamAWins() {
		return teamAWins;
	}
	public void setTeamAWins(int teamAWins) {
		this.teamAWins = teamAWins;
	}
	public int getTeamBWins() {
		return teamBWins;
	}
	public void setTeamBWins(int teamBWins) {
		this.teamBWins = teamBWins;
	}
	public String getDivisionName() {
		return divisionName;
	}
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}
	public League getLeague() {
		return league;
	}
	public void setLeague(League league) {
		this.league = league;
	} 
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toOpponentsDisplay(String teamName) {
		String team = teamBName ; 
		if(team.equals(teamName)) {
			team = teamAName ; 
		}
		// String display = String.format("%0$-12s", team) + " " + this.date ; // attempting to line up the dates. Doesn't work.
		String display = team + " \t" + this.date ;
		return display ; 
	}
	
	public String toString() {
		String matchDisplay = this.teamAName + " v " + this.teamBName + " in " + this.divisionName + " on " + this.date ;
		if(this.id > 0) {
			matchDisplay += " id: " + this.id ; 
		}
		return matchDisplay ; 
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, divisionName, id, league, teamAName, teamBName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if(this.id > 0 && this.id == other.id)
			return true ;
		boolean isEqual = this.teamAName.equals(other.teamAName) ;
		isEqual = isEqual && this.teamBName.equals(other.teamBName) ;
		isEqual = isEqual && this.date.equals(other.date) ;
		isEqual = isEqual && this.divisionName.equals(other.divisionName) ;
		return isEqual ; 
	}

	public boolean canAddWins() {
		boolean canAddWins = this.isValidMatch() ; 
		canAddWins = canAddWins && this.id > 0 ; // Must have match in database.
		canAddWins = canAddWins && (this.teamAWins + this.teamBWins) > 0 ; // Must have some wins
		return canAddWins ;
	}

	public String toScheduleString() {
		return this.teamAName + " vs " + this.teamBName ;
	}
}
