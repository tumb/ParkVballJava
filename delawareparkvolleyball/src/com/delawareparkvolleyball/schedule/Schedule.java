package com.delawareparkvolleyball.schedule;

import java.util.ArrayList;
import java.util.Date;

import com.delawareparkvolleyball.database.MySqlReadWriteUpdate;

public class Schedule {

	private Team teamA ; 
	private Team teamB ; 
	private League league ; 
	int division ; 
	Date dayOfPlay ; 
	
	public Schedule (Team teamA, Team teamB, League league, int division, Date dayOfPlay ) {
		this.teamA = teamA ; 
		this.teamB = teamB ; 
		this.league = league ; 
		this.division = division ; 
		this.dayOfPlay = dayOfPlay ; 
	}
	
	public Schedule createSchedule(int leagueId) {
		League league = MySqlReadWriteUpdate.fetchLeague(leagueId) ; 
		ArrayList<MatchResult> completedMatches = MySqlReadWriteUpdate.fetchAllResults(leagueId, league) ; 
		
		ArrayList<Schedule> allPossibles = calculateAllUnplayedMatches(league, completedMatches) ;
		
/**
 * find all match reults for that league
 * calculate the unplayed matches
 * while not done ...
 *   select unplayed matches for each team. 
 *   done? 
 *   if not done
 *     figure out who played least recently and match them		
 */
		return null ; 
	}

	private ArrayList<Schedule> calculateAllUnplayedMatches(League league2,
			ArrayList<MatchResult> completedMatches) {
		ArrayList<Schedule> unplayedMatches = new ArrayList<Schedule>() ;
		ArrayList<Team> allTeams = league.getAllTeams() ;
		for ( Team teamA : allTeams) {
			for ( Team teamB : allTeams) {
				if(teamA != teamB) {
					if (!isAlreadyPlayed(teamA, teamB, completedMatches)) {
						Schedule unplayedSchedule = new Schedule(teamA, teamB, league, 0, null) ; 
					}
				}
			}
		}
		
		return unplayedMatches ;
	}

	private boolean isAlreadyPlayed(Team teamA2, Team teamB2,
			ArrayList<MatchResult> unplayedMatches) {
		boolean hasPlayed = false ;
		for(int i = 0 ; i < unplayedMatches.size() && !hasPlayed ; i++ ) {
			MatchResult matchResult = unplayedMatches.get(i) ; 
			hasPlayed = hasPlayed || matchResult.containsBoth(teamA, teamB) ; 
		}
		return hasPlayed ;
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



	public int getDivision() {
		return division;
	}



	public Date getDayOfPlay() {
		return dayOfPlay;
	}
	
}
