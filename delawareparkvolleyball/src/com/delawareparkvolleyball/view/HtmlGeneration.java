package com.delawareparkvolleyball.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.delawareparkvolleyball.database.MySqlReadWriteUpdate;
import com.delawareparkvolleyball.schedule.League;
import com.delawareparkvolleyball.schedule.Schedule;

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
}
