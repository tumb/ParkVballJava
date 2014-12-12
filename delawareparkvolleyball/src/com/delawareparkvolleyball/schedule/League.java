package com.delawareparkvolleyball.schedule;

import java.util.ArrayList;

/**
 * @author thom
 * All the teams in one league with other necessary information.
 */
public class League {
	private int year ; 
	private DayOfTheWeek night ; 
	private ArrayList<Team> allTeams ;
	
	public static League CreateExampleLeague(int teamCount) {
		ArrayList<Team> teams = new ArrayList<Team>(teamCount) ;
		int year = 2015 ; 
		DayOfTheWeek night = DayOfTheWeek.THURSDAY ; 
		for(int i = 0 ; i < teamCount ; i++) {
			teams.add(Team.CreateExampleTeam(year, night)) ;
		}
		League newLeague = new League(teams, year, night) ;
		return newLeague ; 
	}
	
	public League(ArrayList<Team> teams, int year, DayOfTheWeek night) {
		this.allTeams = teams ; 
		this.year = year ;
		this.night = night ; 
	}
	public int getYear() {
		return year;
	}
	public DayOfTheWeek getNight() {
		return night;
	}
	public ArrayList<Team> getAllTeams() {
		return allTeams;
	}
	
	public String teamsTable() {
		String html  = "<table>\n" ;
		int teamCount = this.allTeams.size() ; 
		for(int i = 0 ; i < teamCount ; i++) {
			if(i % 2 == 0) {
				html += "\t<tr {background: #CC0}>" ;
			}
			else {
				html += "\t<tr>" ; 
			}
				
			html += "\t<tr> " ; //TODO: use  datatables.net  jquery plugin
			html += "\t\t<td>" + this.allTeams.get(i).getMan().getFirstName() + "</td>" ; 
			html += "\t\t<td>" + this.allTeams.get(i).getWoman().getFirstName() + "</td>" ; 
			html += "\t</tr> " ; 
		}
		html += "</table>" ; 
		return html ; 
	}
	
}
