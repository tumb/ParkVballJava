package com.delawareparkvolleyball.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import com.delawareparkvolleyball.database.MySqlReadWriteUpdate;
import com.delawareparkvolleyball.schedule.League;
import com.delawareparkvolleyball.schedule.Schedule;
/**
 * Comment added Sept 20th from my home computer.
 * @author thom
 *
 */
public class HtmlGeneration {

	public static String htmlOfSchedule(int leagueId) {
		League league = MySqlReadWriteUpdate.fetchLeague(leagueId);
		ArrayList<Schedule> allScheduledMatches = MySqlReadWriteUpdate.fetchAllScheduledMatches(leagueId, league) ;
		Date playdate = allScheduledMatches.get(0).getDayOfPlay() ; // Sloppy to take the first row and use that date.  
		
		String html = "<table border=\"1\" style=\"border-collapse:collapse; border-color:#884444\" >\n";
		html += HtmlGeneration.generateScheduleHeader(league, playdate) ;
		int teamCount = allScheduledMatches.size();
		for (int i = 0; i < teamCount; i++) {
			Schedule schedule = allScheduledMatches.get(i) ; 
			if (i % 2 == 0) {
				html += "\t<tr bgcolor=\"#ffff88\">\n";
			} else {
				html += "\t<tr>\n";
			}
			html += "\t\t<td align=\"center\"> " + schedule.getTeamA().getTeamName() + " vs " ;
			html += schedule.getTeamB().getTeamName()	+ " </td>";

			html += "\t</tr> ";
		}
		html += "</table>";
		return html;
	}


	private static String generateScheduleHeader(League league, Date date) {
		String dateString = "Next Week" ; 
		if (date != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d") ; 
			dateString = simpleDateFormat.format(date) ; 
		}
		String html = "<tr>" ; 
		html += "\n\t<td align=\"center\">" + dateString + "</td>" ; 
		html += "\n</tr>" ;
		return html ; 
	}


	/**
	 * Makes the HTML that sits on top of the standings table. 
	 * @return
	 */
	public static String generateStandingsHeader(ArrayList<Date> weeks) {
		String html = "<tr>" ; 
		html += "\n\t<td colspan=\"2\">Team</td>" ; 
		html += "\n\t<td colspan=\"2\">Total</td>"; 
		html += "\n</tr>" ;

		html += "\n<tr>" ; 
		html += "\n\t<td colspan=\"2\">Players</td>" ; 
		html += "\n\t<td>W</td>" ; 
		html += "\n\t<td>L</td>" ; 
		html += "\n</tr>" ;

		return html ; 
	}
	/**
	 * <table> 
	<tr><td><input type="radio" name="league" value="Monday">Monday</td><td>2015</td><tr>
	<tr><td><input type="radio" name="league" value="Thursday">Thursday</td><tr>
		</table>
	 * @return
	 */
	public static String makeLeagueSelectionTable(HttpSession session) {
		System.out.println("makeLeagueSelectionTable()") ; 
		ArrayList<League> allLeagues = MySqlReadWriteUpdate.fetchLeagueList() ;
		int leagueIdInsession = getLeagueIdFromSession(session) ; 
		System.out.println("leagueSelection() leagueIdInSession: " + leagueIdInsession) ; 
		String rowStart = "\n\t<tr><td><input type=\"radio\" name=\"league\" value=\"" ;
		
		String rowPart2 = "\"" ;
		String rowPart2_5 = ">" ; 
		String rowPart3 = "</td><td>" ; 
		String rowEnd = "</td><tr>" ; 
		
		String html = "\n<table> " ; 
		for (League league : allLeagues) {
			String day = league.getNight().toString() ;
			int year = league.getYear() ; 
			html += rowStart + league.getId() + rowPart2  ; 
			if(league.getId() == leagueIdInsession) {
				html += " checked=\"true\" " ; 
			}
			html += rowPart2_5 + day + rowPart3 + year + rowEnd ; 
		}
		html += "\n</table>" ; 
		return html ; 
	}
	
	public static int getLeagueIdFromSession(HttpSession session) {
		System.out.println("getLeagueIdFromSession begins.") ;
		int leagueId = -1 ; // No id available
		Object storedId = session.getAttribute("leagueId") ; 
		if(storedId != null) { // Could put this into a try/catch to be safe
			leagueId = Integer.parseInt(storedId.toString()) ; 
		}
		System.out.println("getLeagueIdFromSession ends.") ;
		return leagueId ;
	}


	public static String matchResultEditTable(HttpSession session) {
		System.out.println("matchResultEditTable() begins") ; 
		
		int leagueId = getLeagueIdFromSession(session) ;
		League league = MySqlReadWriteUpdate.fetchLeague(leagueId) ;
		ArrayList<Schedule> scheduledMatches = MySqlReadWriteUpdate.fetchAllScheduledMatches(leagueId, league) ;
		String html = "" ; 
		String rowStart = "\n<tr>\n\t<td>" ; 
		String rowPart_1 = "</td>\n\t<td><input type=\"text\" name=\"" ;
		String rowPart_2 = "\" />\n\t<td>" ;
		String rowPart_3 = "</td>\n\t<td><input type=\"text\" name=\"" ; 
		String rowEnd = "\" /></td><tr>" ;
		for (Schedule schedule : scheduledMatches) {
			String matchName = schedule.getMatchName() ; 
			html += rowStart + schedule.getTeamA().getTeamName() ;
			html += rowPart_1 + matchName + "_A" ; 
			html += rowPart_2 + schedule.getTeamB().getTeamName() ;
			html += rowPart_3 + matchName + "_B" ; 
			html += rowEnd ; 
		}
		return html ; 
	}
	
	public static String getSessionLeagueName(HttpSession session) {
		System.out.println("getSessionLeagueName() begins") ; 
		String leagueName = "No League Selected" ;
		int leagueId = getLeagueIdFromSession(session) ;
		if(leagueId > -1) {
			League league = MySqlReadWriteUpdate.fetchLeague(leagueId) ;
			leagueName = league.getName() ;
		}
		System.out.println("getSessionLeagueName() ends") ; 
		return leagueName ;
	}
	
}
