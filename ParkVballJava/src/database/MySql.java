package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import controller.League;
import controller.Match;
import controller.Player;
import controller.Team;
import controller.TeamRecentStandings;
import controller.TeamStandings;
import javafx.collections.ObservableList;

public class MySql {

	private static final String MySqlVballUrl = "jdbc:mysql://intelstor.cqb93xnsbho4.us-east-2.rds.amazonaws.com, 3306" ;
	private static final String vballUsername = "parkvball" ; 
	private static final String vballPassword = "idiot123" ; 
	
	private Connection MySqlVballConnection ; 
	
	public static void main(String[] arguments) {
		System.out.println("MySql main start.") ;
		MySql mySqlVball = new MySql() ; 
		mySqlVball.run() ; 
		System.out.println("MySql main end.") ;
	}

	public MySql() {
		init() ;
	}
	
	public void run() {
		init() ;
		runExample() ; 
		close() ; 
	}
	
	private void init() {
		this.MySqlVballConnection = getMyConnection(MySqlVballUrl, vballUsername, vballPassword) ; 
	}

	private Connection getMyConnection(String MySqlVballUrl, String username, String password) {
		try{
			this.MySqlVballConnection = DriverManager.getConnection(MySqlVballUrl, username, password) ; 
		}
		catch(SQLException sqlException) {
			System.out.println("Failed to get connection") ;
			System.out.println("sqlException.getMessage(): " + sqlException.getMessage()) ;
		}
		return this.MySqlVballConnection ; 
	}

	public String[] fetchYearList(boolean isAscending) {
		String query = "select distinct year from parkvball.league order by year " ;
		if(isAscending) {
			query += " asc " ; 
		}
		else {
			query += " desc " ; 
		}
		
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> allYears = new ArrayList<String>() ;
			while( resultSet.next() ) {
				allYears.add("" + resultSet.getInt("year")) ; 
			}
			return allYears.toArray(new String[0] ); 
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}
	
	public String[] fetchDayList() {
		String query = "select distinct day from parkvball.league " ;
		
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> allDays = new ArrayList<String>() ;
			while( resultSet.next() ) {
				allDays.add(resultSet.getString("day")) ; 
			}
			return allDays.toArray(new String[0] ); 
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}
	
	public String[] fetchDivisionNameList() {
		String query = "select distinct divisionName from parkvball.division " ;
		
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> allDivisions = new ArrayList<String>() ;
			while( resultSet.next() ) {
				allDivisions.add(resultSet.getString("divisionName").toLowerCase()) ; 
			}
			return allDivisions.toArray(new String[0] ); 
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}
	
	private void runExample() {
		String testQuery = "select s.scheduleid, s.matchdate, t1.teamname team1, team1wins, t2.teamname team2, team2wins, d.divisionname, s.leagueid from parkvball.schedule s " ;
		testQuery += "join parkvball.team t1 on t1.teamid = s.team1 " ;
		testQuery += "join parkvball.team t2 on t2.teamid = s.team2 " ;
		testQuery += "join parkvball.division d on d.divisionid = s.divisionid " ;
		testQuery += "where  " ;
		testQuery += "	  s.leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021) " ;
		testQuery += "    and s.matchdate = '2021-08-12' " ;
		testQuery += "    and d.DivisionName = 'blue'"  ;
		String testQuery2 = "select teamname from parkvball.team" ;  // This query works
		System.out.println("testQuery: " + testQuery) ; 
		
		String testInsert = "insert into parkvball.player (Firstname, Lastname, Gender, email, phone) values ('Eric', 'Russ', 'M', 'Ericruss50@Yahoo.Com', '1 (716) 545-7278') " ;
		
		try {
			Statement testStatement = this.MySqlVballConnection.createStatement() ;
			ResultSet testResultSet = testStatement.executeQuery(testQuery) ;
			while(testResultSet.next()) {
				String match = testResultSet.getString("team1") ;
				match += " vs " + testResultSet.getString("team2") ;
				System.out.println(match) ;
			}
//			testStatement.executeUpdate(testInsert) ;
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
		} 
	}
	
	private void close() {
		try {
			this.MySqlVballConnection.close();
			System.out.println("Connection closed.") ;
		} catch (SQLException sqlException) {
			System.out.println("sqlException.getMessage(): " + sqlException.getMessage()) ;
		}
	}

	public String[] fetchTeamNamesByDivision(League selectedLeague) {
		String query = "select t.teamname from parkvball.team t "
				+ " join parkvball.league l on t.LeagueId = l.LeagueId "
				+ " join parkvball.division d on t.divisionId = d.divisionId "
				+ " where l.Year = " + selectedLeague.getYear() 
				+ " and l.Day = '" + selectedLeague.getDayOfWeek() 
				+ "' and d.DivisionName = '" + selectedLeague.getDivisionName() + "' "
				+ " order by teamname asc  " ;

		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> teamNames = new ArrayList<String>() ;
			while( resultSet.next() ) {
				teamNames.add(resultSet.getString("teamname")) ; 
			}
			return teamNames.toArray(new String[0] ); 
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}

	public Match[] fetchMatchesForTeam(League selectedLeague, String teamName) {
		// We want the equivalent of this:
//		select s.scheduleid, s.matchdate, t1.teamname team1, t2.teamname team2, d.divisionname from schedule s
//		join team t1 on t1.teamid = s.team1
//		join team t2 on t2.teamid = s.team2
//		join division d on d.divisionid = s.divisionid
//		where 
//			s.leagueid = (select LeagueId from parkvball.league where Day = 'Monday' and Year = 2020)
//		    and (t1.teamname = 'Paulo' or t2.teamname = 'Paulo') ;
		String query = "select s.matchdate, t1.teamname team1, t2.teamname team2, s.team1wins, s.team2wins, d.divisionname, s.scheduleid from parkvball.schedule s "
				+ " join parkvball.team t1 on t1.teamid = s.team1 "
				+ " join parkvball.team t2 on t2.teamid = s.team2 "
				+ " join parkvball.division d on d.divisionid = s.divisionid "
				+ " where "
				+   " s.leagueid = (select LeagueId from parkvball.league where Day = '" + selectedLeague.getDayOfWeek() + "' "
				+   " and Year = " + selectedLeague.getYear() + ") "
				+   " and (t1.teamname = '" + teamName + "' or t2.teamname = '" + teamName + "') "
				+ " order by matchdate desc " ;
		
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<Match> matches = new ArrayList<Match>() ;
			while( resultSet.next() ) {
				String teamA = resultSet.getString("team1") ;
				int teamAwins = resultSet.getInt("team1wins") ;
				String teamB = resultSet.getString("team2") ;
				int teamBwins = resultSet.getInt("team2wins") ;
				String matchDate = resultSet.getString("matchdate") ;
				String divisionName = resultSet.getString("divisionname") ;
				int id = resultSet.getInt("scheduleid") ;
				matches.add(new Match(teamA, teamAwins, teamB, teamBwins, matchDate, divisionName, id)) ; 
			}
			return matches.toArray(new Match[0] ); 
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new Match[0] ; // If the fetch fails return an empty array.
	}

	/**
	 * 
	 * @param league
	 * @return
	 * SQL: 
	 * select t.teamname, t.teamid, t.divisionid, m.firstname, m.lastname, f.firstname, f.lastname, d.divisionvalue   from parkvball.team t 
join parkvball.player m on m.playerid = t.MaleId
join parkvball.player f on f.playerid = t.FemaleId
join parkvball.division d on t.divisionid = d.divisionid
where t.leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021)
;
	 */
	public ArrayList<TeamStandings> fetchTeamsForStandings(League league) {
		String query = "select t.teamname, t.teamid, t.divisionid, m.firstname mfirst, m.lastname mlast, f.firstname ffirst, f.lastname flast, d.divisionname " ;
		query += " from parkvball.team t " ;
		query += " join parkvball.player m on m.playerid = t.MaleId " ;
		query += " join parkvball.player f on f.playerid = t.FemaleId " ;
		query += " join parkvball.division d on t.divisionid = d.divisionid " ;
		query += " where t.leagueid = " ;
		query += this.buildLeagueSubquery(league) ;
		ArrayList<TeamStandings> teamStandings = new ArrayList<TeamStandings>() ; 
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				String maleFirstname = resultSet.getString("mfirst") ;
				String maleLastname = resultSet.getString("mlast") ;
				String femaleFirstname = resultSet.getString("ffirst") ;
				String femaleLastname = resultSet.getString("flast") ;
				String divisionName = resultSet.getString("divisionname") ;
				String teamName = resultSet.getString("teamname") ;
				TeamStandings team = new TeamStandings(maleFirstname + " " + maleLastname, femaleFirstname + " " + femaleLastname, divisionName ) ;
				team.setTeamName(teamName); ;
				teamStandings.add(team) ; 
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return teamStandings ; 
		
	}
	
	public Match[] fetchMatchesForDivisionAndDate(League league, String matchDate) {
		// We want the equivalent of this:
//		select s.scheduleid, s.matchdate, t1.teamname team1, t2.teamname team2, d.divisionname from schedule s
//		join team t1 on t1.teamid = s.team1
//		join team t2 on t2.teamid = s.team2
//		join division d on d.divisionid = s.divisionid
//		where 
//			s.leagueid = (select LeagueId from parkvball.league where Day = 'Monday' and Year = 2020)
//		    and (t1.teamname = 'Paulo' or t2.teamname = 'Paulo') ;
		String query = "select s.matchdate, t1.teamname team1, t2.teamname team2, d.divisionname, s.scheduleid from parkvball.schedule s "
				+ " join parkvball.team t1 on t1.teamid = s.team1 "
				+ " join parkvball.team t2 on t2.teamid = s.team2 "
				+ " join parkvball.division d on d.divisionid = s.divisionid "
				+ " where "
				+   "	s.leagueid = (select LeagueId from parkvball.league where Day = '" + league.getDayOfWeek() + "' "
				+   "   and Year = " + league.getYear() + ") "
				+   "   and matchDate = '" + matchDate + "' "
				+   "   and d.divisionname = '" + league.getDivisionName() + "' "
				+   " order by team1 asc " ;
		
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<Match> matches = new ArrayList<Match>() ;
			while( resultSet.next() ) {
				String teamA = resultSet.getString("team1") ;
				String teamB = resultSet.getString("team2") ;
				String divisionName = resultSet.getString("divisionname") ;
				int scheduleId = resultSet.getInt("scheduleid") ; 
				matches.add(new Match(teamA, teamB, matchDate, divisionName, scheduleId)) ; 
			}
			return matches.toArray(new Match[0] ); 
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new Match[0] ; // If the fetch fails return an empty array.
	}

	/**
	 * -- insert the schedule not yet played
insert into parkvball.schedule (MatchDate, Team1, team1wins, Team2, team2wins, LeagueId, DivisionId) values (
'2021-08-12', -- MatchDate
(select TeamId from parkvball.team where TeamName = 'Alison' and leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021)),
0, -- team 1 wins
(select TeamId from parkvball.team where TeamName = 'Thom' and leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021)), 
0, -- team 2 wins
(select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021), -- league id
(select DivisionId from parkvball.division 
			where DivisionName = 'green' 
            and LeagueId = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021))
); 
	 * @param selectedMatches
	 * @param league
	 * @param matchDate
	 */
	public boolean insertMatches(ArrayList<Match> selectedMatches, League league, String matchDate) {

		boolean success = true ; 
		String startingSql = "insert into parkvball.schedule (MatchDate, Team1, team1wins, Team2, team2wins, LeagueId, DivisionId) values ( " ;
		startingSql += " '" + matchDate + "', (select TeamId from parkvball.team where TeamName = '" ; 
		String leagueIdSubQuery = " (select LeagueId from parkvball.league where Day = '" + league.getDayOfWeek() + "' and year = " + league.getYear() + " ) " ; ;
		String divisionSubQuery = " (select DivisionId from parkvball.division where DivisionName = '" + league.getDivisionName() + 
				"' and LeagueID = " + leagueIdSubQuery + ")" ; 
		String fullSql = "before try statement " ;
		try {
			Statement submitStatement = this.MySqlVballConnection.createStatement() ;
			for(int i = 0 ; i < selectedMatches.size();  i++) {
				Match match = selectedMatches.get(i) ;
				if(match.getId() < 1) { // Don't create a match if it already exists.
					fullSql = startingSql + match.getTeamAName() + "' and leagueid = " + leagueIdSubQuery + " )," ;
					fullSql += " 0," ; // No team 1 wins for this insert.
					fullSql += " (select TeamId from parkvball.team where TeamName = '" + match.getTeamBName() + "' and leagueid = " + leagueIdSubQuery + " ),"  ;
					fullSql += " 0," ; // No team 2 wins for this insert.
					fullSql += leagueIdSubQuery + ", " ;
					fullSql += divisionSubQuery + ") " ; 
					int result = submitStatement.executeUpdate(fullSql) ;
					success = success && (result == 1) ;
				}
			}
		} catch (SQLException exception) {
			success = false ; 
			System.out.println(exception.getMessage()) ;
			System.out.println("sql: " + fullSql) ; 
		} 
		return success ; 

	}

	public String[] fetchMatchDates(League league) {
		String query = "select distinct s.matchDate from parkvball.schedule s join parkvball.league l on s.LeagueId = l.LeagueId " ;
		int year = 2021 ; 
		if(league != null && ! league.isMissingDayOrYear()) {
			year = league.getYear() ; 
			query += " where l.Year = " + year 
			+ " and l.Day = '" + league.getDayOfWeek() + "' " ;
		}
		else {
			query += "where l.Year >= " + year ; 
		}
		query += " order by matchDate desc " ; 

		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> matchDates = new ArrayList<String>() ;
			while( resultSet.next() ) {
				matchDates.add(resultSet.getString("matchDate")) ; 
			}
			return matchDates.toArray(new String[0] ); 
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}
/**
 * 
 * @param selectedLeague
 * @param matchDate
 * @return
 * select s.scheduleid, s.matchdate, t1.teamname team1,  s.team1wins wins1, t2.teamname team2, s.team2wins wins2, d.divisionname, s.leagueid from schedule s
join team t1 on t1.teamid = s.team1
join team t2 on t2.teamid = s.team2
join division d on d.divisionid = s.divisionid
where 
	s.leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021)
    and s.matchdate = '2021-08-12'
    and d.DivisionName = 'green'
 -- and Team1Wins + Team2Wins < 1
;
 */
	public ArrayList<Match> fetchMatches(League league, String matchDate) {
		ArrayList<Match> matches = new ArrayList<Match>() ; 
			String query = "select s.scheduleid, t1.teamname team1,  s.team1wins wins1, t2.teamname team2, s.team2wins wins2, d.divisionname "
					+ " from parkvball.schedule s  "
					+ " join parkvball.team t1 on t1.teamid = s.team1 "
					+ " join parkvball.team t2 on t2.teamid = s.team2 "
					+ " join parkvball.division d on d.divisionid = s.divisionid "
					+ " where s.leagueid = " ;
			query += " (select LeagueId from parkvball.league where Day = '" + league.getDayOfWeek() + "' and year = " + league.getYear() +")" ;   
			query += " and s.matchdate = '" + matchDate + "' " ;
			try {
				Statement statement = this.MySqlVballConnection.createStatement() ;
				ResultSet resultSet = statement.executeQuery(query) ;
				while( resultSet.next() ) {
					int scheduleid = resultSet.getInt("scheduleid") ; 
					String teamA = resultSet.getString("team1") ; 
					int teamAwins = resultSet.getInt("wins1") ; 
					String teamB = resultSet.getString("team2") ; 
					int teamBwins = resultSet.getInt("wins2") ;
					String divisionName = resultSet.getString("divisionname") ;
					Match match = new Match(league, teamA, teamAwins, teamB, teamBwins, matchDate, divisionName, scheduleid) ;
					matches.add(match) ; 
				}
			} catch (SQLException exception) {
				System.out.println(exception.getMessage()) ;
				System.out.println("query: " + query) ; 
			} 
			return matches ; 
	}

	
	public int deleteMatches(ArrayList<Match> selectedMatches) {
		String query = "delete from parkvball.schedule where scheduleid = " ; 
		String fullQuery = "Not yet used." ;
		int deletedMatches = 0 ; 
		try {
			Statement statement = this.MySqlVballConnection.createStatement();
			for(int i = 0; i < selectedMatches.size(); i++) {
				Match match = selectedMatches.get(i) ;
				int id = match.getId() ; 
				if(id > 0) {
					fullQuery = query + " " + id ; 
					statement.executeUpdate(fullQuery) ;
				}
				deletedMatches++ ; 
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("Before exception, deletedMatches: " + deletedMatches) ; 
			System.out.println("fullQuery: " + fullQuery) ; 
		}
		return deletedMatches ; 
	}

	/**
	 * update schedule set team1wins = 1, team2wins = 1 where scheduleid = 1220;
	 * @param match
	 */
	public boolean updateWinsOneMatch(Match match) {
		String query = "update parkvball.schedule set team1wins = "+ match.getTeamAWins() ;
		query += ", team2wins = " + match.getTeamBWins() ;
		query += " where scheduleid = " + match.getId() ; 
		boolean success = false ; 
		try {
			Statement statement = this.MySqlVballConnection.createStatement();
			statement.executeUpdate(query) ;
			success = true ; 
		} catch (SQLException exception) {
			success = false ;
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		}
		return success ; 
	}

	/**
	 * update schedule 
	 * 		set team1 = (select TeamId from team where teamname = 'Michele K' and leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021))
	 *		, team2 =  (select TeamId from team where teamname = 'Michele K' and leagueid = (select LeagueId from parkvball.league where Day = 'Thursday' and Year = 2021))
	 *		, team1wins = 
	 *		, team2wins = 
			where scheduleid = 1112 ;

	 * @param match
	 * @return
	 */
	public boolean updateTeamsOneMatch(Match match, League league) {
		String query = "update parkvball.schedule set " ;
		query += " team1 = (select TeamId from parkvball.team where teamname = '" + match.getTeamAName() + "' "
				+ " and leagueid = " + buildLeagueSubquery(league) + ")" ;
		query += ", team2 = (select TeamId from parkvball.team where teamname = '" + match.getTeamBName() + "' "
				+ " and leagueid = " + buildLeagueSubquery(league) + ")" ;
		query += ", team1wins = " + match.getTeamAWins() ;
		query += ", team2wins = " + match.getTeamBWins() ;
		query += " where scheduleid = " + match.getId() ; 
		boolean success = false ; 
		try {
			Statement statement = this.MySqlVballConnection.createStatement();
			statement.executeUpdate(query) ;
			success = true ; 
		} catch (SQLException exception) {
			success = false ;
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		}
		return success ; 
	}

	public ArrayList<Match> fetchMatchesForLeagueTeamAndDate(League league, String date, String teamName) {
		ArrayList<Match> matches = new ArrayList<Match>() ; 
		String query = "select s.scheduleid, t1.teamname team1,  s.team1wins wins1, t2.teamname team2, s.team2wins wins2, d.divisionname "
				+ " from parkvball.schedule s  "
				+ " join parkvball.team t1 on t1.teamid = s.team1 "
				+ " join parkvball.team t2 on t2.teamid = s.team2 "
				+ " join parkvball.division d on d.divisionid = s.divisionid "
				+ " where s.leagueid = " ;
		query += " (select LeagueId from parkvball.league where Day = '" + league.getDayOfWeek() + "' and year = " + league.getYear() +")" ;   
		query += " and s.matchdate = '" + date + "' " ;
		query += " and (t1.teamname = '" + teamName + "' or t2.teamname = '" + teamName + "')" ;
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				int scheduleid = resultSet.getInt("scheduleid") ; 
				String teamA = resultSet.getString("team1") ; 
				int teamAwins = resultSet.getInt("wins1") ; 
				String teamB = resultSet.getString("team2") ; 
				int teamBwins = resultSet.getInt("wins2") ;
				String divisionName = resultSet.getString("divisionname") ;
				Match match = new Match(league, teamA, teamAwins, teamB, teamBwins, date, divisionName, scheduleid) ;
				matches.add(match) ; 
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return matches ; 
	}

	private String buildLeagueSubquery(League league) {
		return " (select LeagueId from parkvball.league where Day = '" + league.getDayOfWeek() + "' and year = " + league.getYear() + " ) " ; 
	}

	public boolean deleteOneMatch(Match deleteMatch) {
		boolean success = false ;
		String query = "delete from parkvball.schedule where scheduleid = " + deleteMatch.getId() ; 
		try {
			Statement statement = this.MySqlVballConnection.createStatement();
			statement.executeUpdate(query) ;
			success = true ; 
		} catch (SQLException exception) {
			success = false ;
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		}
		return success ; 
		
	}

	/**
	 * select s.scheduleid, t1.teamname team1,  s.team1wins wins1, t2.teamname team2, s.team2wins wins2, d.divisionname, d.DivisionValue
	 *  from parkvball.schedule s
join parkvball.team t1 on t1.teamid = s.team1
join parkvball.team t2 on t2.teamid = s.team2
join parkvball.division d on d.divisionid = s.divisionid
where 
	s.leagueid = (select LeagueId from parkvball.league where Day = 'Monday' and Year = 2021)
	 * @param selectedLeague
	 * @return
	 */
	public ArrayList<Match> fetchMatchesForStandings(League league) {
		ArrayList<Match> matches = new ArrayList<Match>() ; 
		String query = "select s.scheduleid, t1.teamname team1,  s.team1wins wins1, t2.teamname team2, s.team2wins wins2, d.divisionname, d.DivisionValue "
				+ " from parkvball.schedule s  "
				+ " join parkvball.team t1 on t1.teamid = s.team1 "
				+ " join parkvball.team t2 on t2.teamid = s.team2 "
				+ " join parkvball.division d on d.divisionid = s.divisionid "
				+ " where s.leagueid = (" ;
		query += this.buildLeagueSubquery(league) + ")" ;   
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				String teamA = resultSet.getString("team1") ; 
				int teamAwins = resultSet.getInt("wins1") ; 
				String teamB = resultSet.getString("team2") ; 
				int teamBwins = resultSet.getInt("wins2") ;
				int divisionValue = resultSet.getInt("divisionValue") ;
				Match match = new Match(league, teamA, teamAwins, teamB, teamBwins, divisionValue) ;
				matches.add(match) ; 
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return matches ; 
	}

	public String[] fetchAllTeamNames(League league) {
		String query = "select t.teamname from parkvball.team t "
				+ " join parkvball.league l on t.LeagueId = l.LeagueId "
				+ " where l.Year = " + league.getYear() 
				+ " and l.Day = '" + league.getDayOfWeek() 
				+ "' "
				+ " order by teamname asc  " ;

		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			ArrayList<String> teamNames = new ArrayList<String>() ;
			while( resultSet.next() ) {
				teamNames.add(resultSet.getString("teamname")) ; 
			}
			return teamNames.toArray(new String[0] ); 
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return new String[0] ; // If the fetch fails return an empty array.
	}

	public ArrayList<Player> fetchAllPlayers() {
		ArrayList<Player> allPlayers = new ArrayList<Player>() ; 
		String query = "select * from parkvball.player order by lastname asc ; " ;
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				int id = resultSet.getInt("playerid") ;
				String firstName = resultSet.getString("FirstName") ; 
				String lastName  = resultSet.getString("LastName") ; 
				String gender = resultSet.getString("Gender") ; 
				String email = resultSet.getString("Email") ; 
				String phone = resultSet.getString("Phone") ;
				Player player = new Player(firstName, lastName, gender, email, phone, id) ;
				allPlayers.add(player) ; 
			}
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return allPlayers ; // If the fetch fails return an empty array.
	}

	public boolean insertPlayer(Player player) {
		boolean success = true ;
		String insertSql = "insert into parkvball.player (Firstname, Lastname, Gender, Email, Phone) " ;
		insertSql += " values (" ;
		insertSql += "'" + player.getFirstName() + "', '" + player.getLastName() + "', " ;   
		insertSql += "'" + player.getGender() + "', '" + player.getEmail() + "', " ;   
		insertSql += "'" + player.getPhone() + "')" ;   
		try {
			Statement submitStatement = this.MySqlVballConnection.createStatement() ;
				if(player.getId() < 1) { // Don't create a match if it already exists.
					int result = submitStatement.executeUpdate(insertSql) ;
					success = success && (result == 1) ;
				}
		} catch (SQLException exception) {
			success = false ; 
			System.out.println(exception.getMessage()) ;
			System.out.println("sql: " + insertSql) ; 
		} 
		return success ; 
	}

	public ArrayList<Player> fetchAllPlayers(boolean wantMen) {
		ArrayList<Player> allPlayers = new ArrayList<Player>() ;
		String genderFlag = "F" ; 
		if(wantMen) {
			genderFlag = "M" ;
		}
		String query = "select * from parkvball.player where gender = '" + genderFlag + "' order by lastname asc ; " ;
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				int id = resultSet.getInt("playerid") ;
				String firstName = resultSet.getString("FirstName") ; 
				String lastName  = resultSet.getString("LastName") ; 
				String gender = resultSet.getString("Gender") ; 
				String email = resultSet.getString("Email") ; 
				String phone = resultSet.getString("Phone") ;
				Player player = new Player(firstName, lastName, gender, email, phone, id) ;
				allPlayers.add(player) ; 
			}
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return allPlayers ; // If the fetch fails return an empty array.
	}

	/**
	 * Called by create teams feature. 
	 * Will return as much team information as the database has for that league.
	 * @param league
	 * @return
	 */
	public ArrayList<Team> fetchTeams(League league) {
		String query = "select t.teamname, m.firstName maleFirstName, m.lastName maleLastName, t.maleId, "
				+ " f.firstName femaleFirstName, f.lastName femaleLastName, t.femaleId, t.teamId, d.divisionName " ;
		query +=  " from parkvball.team t join parkvball.league l on t.LeagueId = l.LeagueId " ;
		query += " join parkvball.player m on t.maleId = m.playerid  " ;
		query += " join parkvball.player f on t.femaleId = f.playerid  " ;
		query += " join parkvball.division d on t.divisionId = d.divisionId " ;
		query +=  " where l.Year = " + league.getYear() + " and l.Day = '" + league.getDayOfWeek() + "' " ;
		query += " order by teamname asc  " ;
		
		ArrayList<Team> teamNames = new ArrayList<Team>() ;
		try {
			Statement statement = this.MySqlVballConnection.createStatement() ;
			ResultSet resultSet = statement.executeQuery(query) ;
			while( resultSet.next() ) {
				String teamName =  resultSet.getString("teamname") ;
				String manFirst = resultSet.getString("maleFirstName") ;
				String manLast = resultSet.getString("maleLastName") ;
				int maleId = resultSet.getInt("maleId") ;
				Player man = new Player(manFirst, manLast, "M", "", "", maleId) ; 
				String womanFirst = resultSet.getString("femaleFirstName") ;
				String womanLast = resultSet.getString("femaleLastName") ;
				int femaleId = resultSet.getInt("femaleId") ;
				Player woman = new Player(womanFirst, womanLast, "F", "", "", femaleId) ; 
				int teamId = resultSet.getInt("teamId");
				String divisionName = resultSet.getString("divisionName") ; 
				Team team = new Team(league, man, woman, teamName, teamId, divisionName) ;
				teamNames.add(team) ;
			}
		} catch (SQLException testException) {
			System.out.println(testException.getMessage()) ;
			System.out.println("query: " + query) ; 
		} 
		return teamNames ; 
	}

	public boolean insertNewTeam(Team team) {
		boolean success = true ;
		String insertSql = "insert into parkvball.team (teamname, maleid, femaleid, leagueid, divisionid) " ;
		insertSql += " values (" ;
		insertSql += "'" + team.getTeamName()+ "', " ;
		int maleid = team.getMan().getId() ;
		if(maleid > 0 ) {
			insertSql += maleid + ", " ;
		}
		else {
			insertSql += "(select playerid from parkvball.player where gender = 'M' and firstname = '" + team.getMan().getFirstName() ;
			insertSql += "' and lastname = '" + team.getMan().getLastName() + "', " ;
		}
		Player woman = team.getWoman(); 
		if(woman.getId() > 0) {
			insertSql += woman.getId() + ", " ;
		}
		else {
			insertSql += "(select playerid from parkvball.player where gender = 'F' and firstname = '" + woman.getFirstName() ;
			insertSql += "' and lastname = '" + woman.getLastName() + "', " ;
		}
		insertSql += buildLeagueSubquery(team.getLeague()) + ", " ;
		String divisionName = team.getDivisionName() ; 
		if(divisionName == null || divisionName.isEmpty() || divisionName.equals("brown") ) {
			insertSql += 0 + " " ;
		}
		else {
			insertSql += " (select divisionid from parkvball.division d where d.leagueid = " + buildLeagueSubquery(team.getLeague()) ;
			insertSql += " and d.divisionname = '" + divisionName + "' " ;
		}
		insertSql += ") " ;
		
		try {
			Statement submitStatement = this.MySqlVballConnection.createStatement() ;
				if(team.getTeamId() < 1) { // Don't create a match if it already exists.
					int result = submitStatement.executeUpdate(insertSql) ;
					success = success && (result == 1) ;
				}
		} catch (SQLException exception) {
			success = false ; 
			System.out.println(exception.getMessage()) ;
			System.out.println("sql: " + insertSql) ; 
		} 
		return success ; 
		
	}

	public ArrayList<TeamRecentStandings> fetchRecentStandings(League selectedLeague, ObservableList<String> dates) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Match> fetchMatches(League league, ObservableList<String> dates) {
		ArrayList<Match> allMatches = new ArrayList<Match>() ; 
		for(int i = 0 ; i < dates.size() ; i++) {
			String date = dates.get(i) ; 
			allMatches.addAll(fetchMatches(league, date)) ; 
		}
		return allMatches ;
	}

}
