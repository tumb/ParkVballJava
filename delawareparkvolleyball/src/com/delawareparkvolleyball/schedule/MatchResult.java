package com.delawareparkvolleyball.schedule;

import java.util.Date;

/**
 * This should be one row in the database - with team and league being links to the 
 * original objects.
 * @author thom
 *
 */
public class MatchResult {
 private Team teamA ; 
 private Team teamB ; 
 private League league ; 
 private int teamAwins ; // Implied - every team A win is a team B loss.
 private int teamBwins ; 
 Date dayOfPlay ; 
 
 public MatchResult(League league, Date dayOfPlay, Team teamA, Team teamB, int teamAwins, int teamBwins) {
	this.league = league ; 
	this.dayOfPlay = dayOfPlay ; 
	this.teamA = teamA ; 
	this.teamB = teamB ; 
	this.teamAwins = teamAwins ; 
	this.teamBwins = teamBwins ; 
 }

public Team getTeamA() {
	return teamA;
}

public Team getTeamB() {
	return teamB;
}

public League getLeague() {
	return league;
}

public int getTeamAwins() {
	return teamAwins;
}

public int getTeamBwins() {
	return teamBwins;
}

public Date getDayOfPlay() {
	return dayOfPlay;
}

/**
 * Look for a match on team A or team B
 * @param team
 * @return A if teamA is correct, B if teamB is correct, N if neither. 
 */
public String containsTeam(Team team) {
	String match = "N" ; 
	if(team.playerMatch(teamA)) {
		match = "A" ;
	}
	if(team.playerMatch(teamB)) {
		match = "B" ;
	}
	
	return match ;
}

public boolean containsBoth(Team teamA2, Team teamB2) {
	boolean containsBoth = this.teamA.equals(teamA2) || this.teamB.equals(teamA2) ;
	containsBoth = containsBoth && (this.teamA.equals(teamB2) || this.teamB.equals(teamB2)) ;
	return containsBoth ;
}

 
 
}
