package com.delawareparkvolleyball.servlets ; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.FutureTask;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.delawareparkvolleyball.database.MySqlReadWriteUpdate;
import com.delawareparkvolleyball.schedule.Person;

@WebServlet("/LeagueEditor")
public class LeagueEditorServlet extends HttpServlet { 

	private static final long serialVersionUID = 1L ;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processLeagueEditRequest(request, response);
	}

	private void processLeagueEditRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// For debugging only
		System.out.println("Inside doPost of LeagueEditorServlet") ;
		Enumeration<String> parameterNames = request.getParameterNames() ;
		String parameterList = "" ; 
		while(parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement() ; 
			parameterList += "\n" +  name + ", " ;
			String parameter = request.getParameter(name) ; 
			parameterList += parameter ; 
		}
		System.out.println(parameterList) ;
		// End of debugging
		
		int league = addOrEditLeagueInputRequest(request); 
		addOrEditTeams(request, league) ; 
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
		requestDispatcher.include(request, response); // TODO learn the difference between include and forward.
//		rd.forward(request, response);
		
	}

	private void addOrEditTeams(HttpServletRequest request, int league) {
		Map<String, String[]> fullParameterMap = request.getParameterMap() ;
		int teamCount = countTeams(fullParameterMap) ;
		if (teamCount > 0) {
			ArrayList<Person> allPeopleFromRequest = addOrEditNewPeople(fullParameterMap, teamCount) ; 
			// We now have all the people and are ready to add teams
			for(int i = 1 ; i <= teamCount ; i++) {
				Person man = getPerson(fullParameterMap, i, true) ;
				Person woman = getPerson(fullParameterMap, i, false) ; 
				String teamNameKey = "team_name_" + i ; 
				String teamName = fullParameterMap.get(teamNameKey)[0] ;
				Person databaseMan = findPersonFromDatabase(man, allPeopleFromRequest) ;
				Person databaseWoman = findPersonFromDatabase(woman, allPeopleFromRequest) ;
				MySqlReadWriteUpdate.insertNewTeam(league, databaseMan, databaseWoman, teamName) ; 
			}
		}
		
	}

	/**
	 * Check each person in parameter list.
	 * If exists - add the database id.
	 * If new - create in database and note the id. 
	 * @param fullParameterMap
	 * @return
	 */
	private ArrayList<Person> addOrEditNewPeople(Map<String, String[]> fullParameterMap, int teamCount) {
		ArrayList<Person> allPeopleInDatabase = MySqlReadWriteUpdate.fetchAllPeopleInDatabase() ;
		ArrayList<Person> allPeopleInRequest = getAllPeopleInRequest(fullParameterMap, teamCount) ;
		for (Person person : allPeopleInRequest) {
			if(allPeopleInDatabase.contains(person)) {
				// Put the id into the person in the request list.
				Person databasePerson = findPersonFromDatabase(person, allPeopleInDatabase) ;
				person.setId(databasePerson.getId()) ; 
			}
			else {
				// insert the person and get the id
				int id = MySqlReadWriteUpdate.insertPerson(person) ;
				person.setId(id) ; 
			}
		}
		return allPeopleInRequest ;
	}

	private Person findPersonFromDatabase(Person person, ArrayList<Person> allPeopleInDatabase) {
		for (Person databasePerson : allPeopleInDatabase) {
			if(person.equals(databasePerson)) {
				return databasePerson ; 
			}
		} 
			
		
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<Person> getAllPeopleInRequest(Map<String, String[]> fullParameterMap, int teamCount) {
		ArrayList<Person> allPeopleInRequest = new ArrayList<Person>(teamCount) ; 
		for(int i = 1 ; i <= teamCount ; i++) {
			Person man = getPerson(fullParameterMap, i, true) ; 
			Person woman = getPerson(fullParameterMap, i, false) ;
			allPeopleInRequest.add(man) ;
			allPeopleInRequest.add(woman) ; 
		}
		return allPeopleInRequest ; 
	}
	
	private Person getPerson(Map<String, String[]> fullParameterMap, int parameterIndex,	boolean isMale) {
		String genderPronoun = "woman" ; 
		String genderInitial = "F" ;
		if(isMale) {
			genderPronoun = "man" ;
			genderInitial = "M" ; 
		}
		String firstNameKey = genderPronoun + "_first_" + parameterIndex ;
		String firstName = fullParameterMap.get(firstNameKey)[0] ; 
		String lastNameKey  = genderPronoun + "_last_"  + parameterIndex ;
		String lastName = fullParameterMap.get(lastNameKey)[0] ; 
		Person person = new Person(firstName, lastName, genderInitial) ; 
		return person;
	}

	private int countTeams(Map<String, String[]> fullParameterMap) {
		int teamCount = 0 ;
		boolean isMoreTeams = true ; 
		while (isMoreTeams) {
			teamCount++ ; 
			String key = "man_first_" + teamCount ; 
			isMoreTeams = fullParameterMap.containsKey(key) ; 
		}
		return teamCount - 1 ;
	}

	private int addOrEditLeagueInputRequest(HttpServletRequest request) {
		String dayOfWeek = request.getParameter("day_of_week") ; 
		int year = Integer.parseInt(request.getParameter("year")) ; 
		int divisionCount = Integer.parseInt(request.getParameter("division_count")) ; 
		MySqlReadWriteUpdate.createNewLeague(dayOfWeek, year, divisionCount) ;
		return 1 ; // TODO For now just returning the only existing league. 
	}

	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException 
	{
		processLeagueEditRequest(request, response);
	}  // End of doGet() - example method. 
}