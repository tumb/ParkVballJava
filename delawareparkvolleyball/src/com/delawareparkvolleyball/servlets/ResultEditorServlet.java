package com.delawareparkvolleyball.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.delawareparkvolleyball.database.MySqlReadWriteUpdate;
import com.delawareparkvolleyball.schedule.League;
import com.delawareparkvolleyball.schedule.MatchResult;
import com.delawareparkvolleyball.schedule.Team;
import com.delawareparkvolleyball.view.HtmlGeneration;

@WebServlet("/ResultEditor")
public class ResultEditorServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L ;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost begins") ; 
		processEditMatchResultsRequest(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet begins") ; 
		processSelectLeagueRequest(request, response);
	}

	private void processEditMatchResultsRequest(HttpServletRequest request,
			HttpServletResponse response)  throws ServletException, IOException {
		// For debugging only
		showRequestParameters(request, "processEditMatchResultsRequest()"); 

		// Need to get all the parameters that refer to matches and organize them in matched pairs.
		ArrayList<MatchResult> matchesInRequest = getMatchesFromParameters(request) ;   
		MySqlReadWriteUpdate.saveMatches(matchesInRequest) ; 
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("matchResultEntry.jsp");
		requestDispatcher.include(request, response); // TODO learn the difference between include and forward.
		
	}

	private ArrayList<MatchResult> getMatchesFromParameters(
			HttpServletRequest request) {
		ArrayList<MatchResult> matches = new ArrayList<MatchResult>() ;
		HttpSession session = request.getSession() ;
		int leagueId = HtmlGeneration.getLeagueIdFromSession(session) ; 
		League league = MySqlReadWriteUpdate.fetchLeague(leagueId) ; 
		
		Enumeration<String> parameterNames = request.getParameterNames() ;
		while(parameterNames.hasMoreElements()) {
			String a_parameterName = parameterNames.nextElement() ;
			if(isMatchName_A(a_parameterName)) {
				String a_value = request.getParameter(a_parameterName) ;
				String b_parameterName = a_parameterName.substring(0, a_parameterName.length() - 2) + "_B" ; 
				System.out.println("b_parameterName: " + b_parameterName) ; 
				String b_value = request.getParameter(b_parameterName) ;
				if(b_value == null) {
					b_value = "0" ; 
				}
				String a_teamName = calculateTeamName_A(a_parameterName) ; 
				int a_wins = parseInt(a_value) ;
				String b_teamName = calculateTeamName_B(b_parameterName) ;
				int b_wins = parseInt(b_value) ; 
				
				if(a_wins > 0 || b_wins > 0) { // don't bother if no wins were entered.
					Team teamA = MySqlReadWriteUpdate.fetchTeam(a_teamName, leagueId) ;
					Team teamB = MySqlReadWriteUpdate.fetchTeam(b_teamName, leagueId) ;
					Date dayOfPlay = new Date() ; 
					MatchResult matchResult = new MatchResult(league, dayOfPlay, teamA, teamB, a_wins, b_wins) ; 
					System.out.println("matchResult: " + matchResult) ;
					matches.add(matchResult) ; 
				}
			}
		}
		return matches ;
	}

	private int parseInt(String a_value) {
		int value = 0 ;
		try {
			value = Integer.parseInt(a_value) ; 
		}
		catch(NumberFormatException numberFormatException) {
			value = 0 ; // technically not required
		}
		return value ;
	}

	private String calculateTeamName_B(String b_parameterName) {
		String[] parts = b_parameterName.split("-") ;
		return parts[1].substring(0, parts[1].length() - 2 );
	}

	private String calculateTeamName_A(String a_parameterName) {
		String[] parts = a_parameterName.split("-") ;  
		return parts[0];
	}

	private static boolean isMatchName_A(String name) {
		boolean isMatchName = name.contains("-") ; // TODO replace this with a varialble - probably defined in Schedule or MatchResult
		isMatchName = isMatchName && name.endsWith("_A") ; 
		return isMatchName ; 		
	}
	
	private String showRequestParameters(HttpServletRequest request, String methodName) {
		System.out.println("---- Parameters in request: " + methodName + " of LeagueEditorServlet") ;
		Enumeration<String> parameterNames = request.getParameterNames() ;
		String parameterList = "" ; 
		while(parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement() ; 
			parameterList += "\n" +  name + ", " ;
			String parameter = request.getParameter(name) ; 
			parameterList += parameter ; 
		}
		parameterList += "\n ---- End of Parameter list ----" ;
		System.out.println(parameterList) ;
		return parameterList ; 
	}

	private void processSelectLeagueRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		showRequestParameters(request, "processSelectLeagueRequest()"); 

		HttpSession session = request.getSession() ;
		String leagueIdAsString = request.getParameter("league") ; 
		session.setAttribute("leagueId", leagueIdAsString);
		// End of debugging
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("matchResultEntry.jsp");
		requestDispatcher.include(request, response); // TODO learn the difference between include and forward.
//		rd.forward(request, response);
		
	}

	
}
