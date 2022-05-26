package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.MySql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import view.ApplicationFX;

public class Controller {
	private MySql mySqlDatabase ;
	private ApplicationFX viewFX ; 
	
	private League selectedLeague ; 
	private String matchDate ; // This must be in yyyy-mm-dd format. It is the match date where we will create or modify league matches. 
	private ArrayList<Match> selectedMatches ; 
	private String[] workingTeamNames ; // The teams currently active in scheduling (or other subtask)
	private boolean isNewTeamList ; // Flag that the set of teams has changed.
	
	private boolean isAdminScreen ; 
	private boolean isResultsScreen ; 
	private boolean isSchedulingScreen ; 
	private boolean isStandingsScreen ; 
	private boolean isDisplaySchedule ;
	private boolean isTeamRecord ;
	private boolean isAddPlayersScreen ;
	private boolean isCreateTeamsScreen ; 
	private boolean isUpdateDivisionsScreen ;
	
	public static void main(String[] arguments) {
		Controller controller = new Controller() ;
		controller.init() ; 
		System.out.println("Controller only has been run.") ;
	}
	
	public void init() {
		if(this.mySqlDatabase == null) {
			this.mySqlDatabase = new MySql() ;
		}
		this.selectedLeague = new League() ; 
		this.selectedMatches = new ArrayList<Match>() ;
	}

	public void setView(ApplicationFX view) {
		this.viewFX = view ; 
	}
	
	public void createLeaguePressed() {
		viewFX.setCreateLeagueScene() ;
	}

	public ObservableList<String> buildYearList() {
		String[] yearArray = fetchYearList(false) ;
		ObservableList<String> yearList = FXCollections.observableArrayList(yearArray);
		return yearList ; 
	}
	
	public ObservableList<String> buildDayList() {
		String[] days = fetchDayList() ;
		ObservableList<String> dayList = FXCollections.observableArrayList(days);
		return dayList ; 
	}
	
	public ObservableList<String> buildDivisionNameList() {
		String[] divisionArray = fetchDivisionNameList() ;
		ObservableList<String> divisionNameList = FXCollections.observableArrayList(divisionArray);
		return divisionNameList ; 
	}
	
	public void leagueSubmitButtonPress() {
		// TODO Auto-generated method stub
		// verify that league can be created
		// create data in database
		// report results to user
		System.out.println("leagueSubmit clicked.") ; 
	}

	public void makeSchedule() {
		viewFX.setMakeSchedulePane() ; 
		setFlagForScreen("Scheduling") ;
	}
	
	public void returnToHomePane() {
		viewFX.returnToHomePane() ;
		setFlagForScreen("Admin") ;
	}

	public void setLeagueDay(String day) { // Set the slected day of week for the league
		this.selectedLeague.setDayOfWeek(day) ; 
		this.isNewTeamList = true ; 
		ObservableList<String> matchDates = buildDateList(this.selectedLeague) ;
		this.viewFX.setNewMatchDates(matchDates) ;
		if(isSchedulingScreen) {
			this.updateSchedulingDisplay() ;
		}
		else if(isStandingsScreen) {
			displayStandings();
		}
		else if(isDisplaySchedule) {
			this.displaySchedule() ;
		}
		else if(this.isTeamRecord) {
			this.displayTeamRecord() ; 
		}
		else if(this.isCreateTeamsScreen) {
			updateExistingTeams() ;
		}
		updateSchedulingDisplay() ; 
	}
	
	public String[] fetchYearList(boolean isAscending) {
		return this.mySqlDatabase.fetchYearList(isAscending) ;
	}
	
	public String[] fetchDayList() {
		return this.mySqlDatabase.fetchDayList() ;
	}

	public String[] fetchDivisionNameList() {
		return this.mySqlDatabase.fetchDivisionNameList() ;
	}

	public void setLeagueYear(String year) {
		this.selectedLeague.setYear(year) ; 
		this.isNewTeamList = true ; 
		ObservableList<String> dateList = buildDateList(this.selectedLeague) ;
		this.viewFX.setNewMatchDates(dateList) ;
		updateSchedulingDisplay() ; 
		if(isResultsScreen) {
			// TODO shouldn't this update results instead?
//			ObservableList<String> matchDates = buildDateList(this.selectedLeague) ;
//			this.viewFX.setNewMatchDates(matchDates) ;
		}
		else if(this.isStandingsScreen) {
			displayStandings() ; 
		}
		else if(this.isDisplaySchedule) {
			displaySchedule(); 
		}
		else if(this.isTeamRecord) {
			this.displayTeamRecord() ; 
		}
		else if(this.isCreateTeamsScreen) {
			updateExistingTeams() ;
		}
	}

	private void updateExistingTeams() {
   		ArrayList<Team> teams = this.mySqlDatabase.fetchTeams(this.selectedLeague) ;
   		this.viewFX.updateLeagueTeamList(teams) ; 
	}

	public void setLeagueDivisionName(String newDivisionName) {
		this.selectedLeague.setDivisionName(newDivisionName) ; 
		this.isNewTeamList = true ;
		updateSchedulingDisplay() ; 
	}

	private void updateSchedulingDisplay() {
		if(isSchedulingScreen && selectedLeague.isLeagueSelected()) {
			// Remove any scheduled matches in memory. They're in the database or to be thrown out.
			this.selectedMatches.clear() ; 
			// Get list of all team names. 
			String[] teamNamesForScheduling = this.mySqlDatabase.fetchTeamNamesByDivision(selectedLeague) ;
			// Put it in teamA and teamB list boxes for selection. 
			this.viewFX.updatesTeamsForScheduling(teamNamesForScheduling) ;
			if(this.matchDate != null && ! this.matchDate.isEmpty()) {
				Match[] storedMatches = mySqlDatabase.fetchMatchesForDivisionAndDate(this.selectedLeague, this.matchDate) ;
				for(int i = 0; i < storedMatches.length ; i++) { // There's Array.asList(storedMatches) that might be used.
					this.selectedMatches.add(storedMatches[i]) ; 
				}
				viewFX.updateScheduledMatchesView(this.selectedMatches) ; 
			}
		}
	}
	
	public void setTeamHistory(String teamAName) {
		Match[] teamMatches = this.mySqlDatabase.fetchMatchesForTeam(this.selectedLeague, teamAName) ; 
		String[] opponentsDisplay = new String[teamMatches.length] ; 
		for(int i = 0  ; i < teamMatches.length ; i++) {
			opponentsDisplay[i] = teamMatches[i].toOpponentsDisplay(teamAName) ; 
		}
		this.viewFX.setOpponentsDisplayInScheduling(opponentsDisplay, teamAName) ;
	}

	public void setTeamRecord(String teamName) {
		Match[] teamMatches = this.mySqlDatabase.fetchMatchesForTeam(this.selectedLeague, teamName) ; 
		this.viewFX.setTeamRecord(teamMatches, teamName) ;
	}

	public void setMatchDate(String newValue) {
		this.matchDate = newValue ; 
		if(isResultsScreen) {
			updateResults() ; 
		}
		else if(isDisplaySchedule) {
			this.displaySchedule();
		}
	}

	public void addMatchToSelections() {
		Match newMatch = viewFX.getInputsToSelectMatch(this.selectedLeague, this.matchDate) ;
		if(newMatch.isValidMatch()) {
			this.selectedMatches.add(newMatch) ; 
			viewFX.updateSchedulingTeamLists(this.selectedMatches, newMatch.getTeamAName(), newMatch.getTeamBName() ) ;
		}
	}
	
	public void removeMatchFromSelections() {
		String displayString = this.viewFX.getCurrentlySelectedMatchFromScheduling() ;
		if(displayString != null && !displayString.isEmpty()) {
			// Find that match
			Match matchToRemove = new Match(displayString) ; 
			this.selectedMatches.remove(matchToRemove) ;
			this.viewFX.updateSchedulingAfterMatchRemoved(this.selectedMatches, matchToRemove);
		}
	}

	public void submitSchedule() {
		boolean isValid = verifyScheduleData() ;
		if(isValid) {
			// Store in database
			boolean succeeded = this.mySqlDatabase.insertMatches(this.selectedMatches, this.selectedLeague, this.matchDate) ;
			// Move to home page
			if(succeeded) {
				this.viewFX.popupWindow("Success!", "Save was successful.") ;
			}
			else {
				this.viewFX.popupWindow("Warning!", "Insert failed.") ;
			}
		}
		else {
			this.viewFX.popupWindow("Warning!", "Missing data") ;
		}
		
	}
	
	// TODO future validation might include making sure the date and dayOfWeek are the same.
	// 		check that each team plays exactly 2 times
	private boolean verifyScheduleData()  {
		boolean isValid = this.matchDate != null && !this.matchDate.isEmpty() ;
		isValid = isValid && selectedMatches.size() > 0 ; 
		isValid = isValid && selectedLeague != null ; 
		return isValid ; 
	}
	
	public League getSelectedLeague() {
		return this.selectedLeague ; 
	}

	public void updateResults() {
		selectedMatches.clear() ;
		if(! (this.selectedLeague == null || this.selectedLeague.isMissingDayYearOrDivision()) && !(matchDate == null || matchDate.isEmpty())) {
			selectedMatches = this.mySqlDatabase.fetchMatches(selectedLeague, matchDate) ;
		}
		this.viewFX.setUpdateResultsPane(selectedMatches) ;
		setFlagForScreen("Results") ;
	}

	private void setFlagForScreen(String string) {
		this.isAdminScreen       = false ; 
		this.isResultsScreen     = false ; 
		this.isSchedulingScreen  = false ; 
		this.isStandingsScreen   = false ;
		this.isDisplaySchedule   = false ; 
		this.isTeamRecord        = false ;
		this.isAddPlayersScreen  = false ; 
		this.isCreateTeamsScreen = false ;
		this.isUpdateDivisionsScreen  = false ; 
		
		if(string.equals("Results")) {
			this.isResultsScreen = true ; 
		}
		else if(string.equals("Admin")) {
			this.isAdminScreen = true ; 
		}
		else if(string.equals("Scheduling")) {
			this.isSchedulingScreen = true ;
		}
		else if(string.equals("Standings" )) {
			this.isStandingsScreen = true ; 
		}
		else if(string.equals("Display Schedule" )) {
			this.isDisplaySchedule = true ; 
		}
		else if(string.equals("Team Record" )) {
			this.isTeamRecord  = true ; 
		}
		else if(string.equals("Add Players" )) {
			this.isAddPlayersScreen   = true ; 
		}
		else if(string.equals("Create Teams" )) {
			this.isCreateTeamsScreen   = true ; 
		}
		else if(string.equals("Update Divisions" )) {
			this.isUpdateDivisionsScreen   = true ; 
		}
		
	}

	public ObservableList<String> buildDateList(League league) {
		String[] matchDates = this.mySqlDatabase.fetchMatchDates(league) ;
		ObservableList<String> matchDateList = FXCollections.observableArrayList(matchDates);
		return matchDateList ; 
	}

	public void deleteAllMatches() {
		this.mySqlDatabase.deleteMatches(this.selectedMatches) ; 
		removeDatabaseMatches() ;
		updateSchedulingDisplay() ; 
	}

	private void removeDatabaseMatches() {
		for(int i = 0 ; i < this.selectedMatches.size(); i++) {
			Match match = this.selectedMatches.get(i) ;
			if(match.getId() > 0 ) {
				this.selectedMatches.remove(match) ;
			}
		}
		
	}

	public void addWinsEventOneMatch(Match match) {
		if(match.canAddWins()) {
			this.mySqlDatabase.updateWinsOneMatch(match) ; 
		}
	}

	public void displayNoShowPane() {
		if(this.selectedLeague.isLeagueSelected() && isValidDate(matchDate)) {
			this.viewFX.displayTeamNoShowPane(this.selectedLeague, this.matchDate) ;
		}
		
	}

	private boolean isValidDate(String date) {
		return ! (date == null || date.isEmpty()) ;
	}

	public String[] fetchTeamList(League league) {
		return this.mySqlDatabase.fetchTeamNamesByDivision(league) ;
	}

	public ArrayList<Match> fetchMatchesForTeam(League league, String date, String teamName) {
		return this.mySqlDatabase.fetchMatchesForLeagueTeamAndDate(league, date, teamName);
	}

	public void mergeMatches(ArrayList<Match> matches, String teamName) {
		boolean success = matches.size() == 2 ; 
		// edit the 2 matches 
		Match updateMatch = matches.get(0) ;
		Match deleteMatch = matches.get(1) ;
		String upA = updateMatch.getTeamAName() ; 
		String upB = updateMatch.getTeamBName() ; 
		String delA = deleteMatch.getTeamAName() ; 
		String delB = deleteMatch.getTeamBName() ; 
		if(teamName.equals(upA) && teamName.equals(delA)) {
			updateMatch.setTeamAName(delB);
		}
		else if(teamName.equals(upA) && teamName.equals(delB)) {
			updateMatch.setTeamAName(delA);
		}
		else if(teamName.equals(upB) && teamName.equals(delA)) {
			updateMatch.setTeamBName(delB);
		}
		else if(teamName.equals(upB) && teamName.equals(delB)) {
			updateMatch.setTeamBName(delA);
		}
		updateMatch.setTeamAWins(0);
		updateMatch.setTeamBWins(0);
		
		success = success && this.mySqlDatabase.updateTeamsOneMatch(updateMatch, this.selectedLeague) ;
		// delete the 2nd match
		success = success && this.mySqlDatabase.deleteOneMatch(deleteMatch) ;
		// popup a success message? 
		this.viewFX.popupWindow("Result", "Merge Succes is " + success) ; 
	}

	public boolean deleteSingleMatch(Match match) {
		boolean success = false ;
		success = this.mySqlDatabase.deleteOneMatch(match) ;
		// remove from controller list of matches?
		// remove from the gridpane
		this.viewFX.removeSingleResultPane(match) ; 
		String resultToDisplay = "Match "+ match.getTeamAName() + " vs " + match.getTeamBName() ;
		if(success) {
			resultToDisplay += " deleted." ;
		}
		else {
			resultToDisplay += " NOT deleted." ;
		}
		this.viewFX.popupWindow("Delete result",  resultToDisplay) ; 
		return success ; 
	}

	public void submitAllResults(ArrayList<Match> allMatches) {
		boolean success = true ; 
		for(Match match : allMatches) {
			success = success && this.mySqlDatabase.updateWinsOneMatch(match) ;
		}
		if(success) {
			this.viewFX.popupWindow("Wins Updated", "All " + allMatches.size() + " were saved.") ;
			returnToHomePane();
		}
		else {
			this.viewFX.popupWindow("Wins Updated", "There was a problem saving new wins.") ;
		}
	}

	public void displayStandings() {
		this.setFlagForScreen("Standings") ;
		// gather data needed for standings from database
		ArrayList<TeamStandings> standings = this.mySqlDatabase.fetchTeamsForStandings(this.selectedLeague) ; 
		// create a map into standings 
		Map<String, Integer> indexMap = buildStandingsMap(standings) ; 
		// get all the matches
		ArrayList<Match> matches = this.mySqlDatabase.fetchMatchesForStandings(this.selectedLeague) ; 
		// fill match data into standings
		standings = this.computePoints(standings, matches, indexMap) ; 
		standings = this.computeRanks(standings) ; 
		// send to the view
		this.viewFX.setStandingsPane(standings); 
	}

	private ArrayList<TeamStandings> computeRanks(ArrayList<TeamStandings> standings) {
		standings.sort(null);
		int currentRank = 0 ; 
		int currentPoints = 0 ;
		int skippedRanks = 0 ; 
		for(int i = 0 ; i < standings.size() ; i++) {
			TeamStandings team = standings.get(i) ;
			int newPoints = team.getPoints() ; 
			if(currentPoints != newPoints) {
				currentRank++ ; 
				currentRank += skippedRanks ; 
				team.setRank(currentRank);
				skippedRanks = 0 ; 
				currentPoints = newPoints ; 
			}
			else if (currentPoints == newPoints) {
				team.setRank(currentRank);
				skippedRanks++ ; 
			}
		}
		return standings ;
	}

	private ArrayList<TeamStandings> computePoints(ArrayList<TeamStandings> standings, ArrayList<Match> matches, Map<String, Integer>indexMap) {
		for(int i = 0 ; i < matches.size() ; i++) {
			Match match = matches.get(i) ;
			int value = match.getDivisionValue() ;
			int wins = match.getTeamAWins() ;
			if(wins > 0) {
				String teamName = match.getTeamAName() ;
				Integer mapIndex = indexMap.get(teamName) ;
				int index = mapIndex.intValue() ;
				TeamStandings team = standings.get(index) ; 
				team.addPoints(wins * value);
			}
			wins = match.getTeamBWins() ;
			if(wins > 0) {
				String teamName = match.getTeamBName() ;
				Integer mapIndex = indexMap.get(teamName) ;
				int index = mapIndex.intValue() ;
				TeamStandings team = standings.get(index) ; 
				team.addPoints(wins * value);
			}
		}
		return standings ;
	}

	private Map<String, Integer> buildStandingsMap(ArrayList<TeamStandings> standings) {
		Map<String, Integer> teamMap = new HashMap<String, Integer>() ;
		for(int i = 0 ; i < standings.size() ; i++) {
			TeamStandings team = standings.get(i) ; 
			String name = team.getTeamName() ; 
			teamMap.put(name, new Integer(i)) ; 
		}
		return teamMap;
	}

	public void displaySchedule() {
		this.setFlagForScreen("Display Schedule") ;
		// gather data needed for standings from database
		ArrayList<Match> matches = this.mySqlDatabase.fetchMatches(selectedLeague, matchDate) ; 
		// send to the view
   		this.viewFX.setDisplaySchedulePane(matches); 
	}

	public void displayTeamRecord() {
		this.setFlagForScreen("Team Record") ;
   		ObservableList<String> teamsToSelect = buildTeamList() ;
   		this.viewFX.setTeamRecordPane(teamsToSelect) ; 
	}

	public ObservableList<String> buildTeamList() {
		String[] teamArray = fetchTeamListForAllDivisions(this.selectedLeague) ;
		ObservableList<String> teamList = FXCollections.observableArrayList(teamArray);
		return teamList ; 
	}

	private String[] fetchTeamListForAllDivisions(League league) {
		String[] teams = new String[0] ;
		if(league.isValid()) {
			teams = this.mySqlDatabase.fetchAllTeamNames(league) ; 
		}
		return teams ; 
	}

	public void displayAddPlayersPane() {
		this.setFlagForScreen("Add Players") ;
   		ObservableList<String> existingPlayers = buildListOfAllPlayers() ;
   		this.viewFX.setAddPlayersPane(existingPlayers) ; 
	}

	private ObservableList<String> buildListOfAllPlayers() {
		ArrayList<Player> allPlayers = this.mySqlDatabase.fetchAllPlayers() ;
		ObservableList<String> playerList = FXCollections.observableArrayList() ;
		for(int i = 0 ; i < allPlayers.size(); i++) {
			playerList.add(allPlayers.get(i).toString()) ; 
		}
		return playerList ; 
	}

	public void submitSubmitNewPlayer(Player player) {
		if(player.isValid()) {
			// Maybe check that the player doesn't already exist
			// add to database
			boolean success = this.mySqlDatabase.insertPlayer(player) ;
			if(success) {
				viewFX.popupWindow("Success", player.getFirstName() + " " + player.getLastName()+ " added.") ;
				viewFX.addToAllPlayersList(player) ;
			}
			// add to list of addPlayersPane
		}
		else {
			viewFX.popupWindow("Invalid Player", "Player submitted is: " + player.toString()) ; 
		}
		
	}

	public void displayCreateTeamsPane() {
		this.setFlagForScreen("Create Teams") ;
   		this.viewFX.setCreateTeamsPane() ;
   		boolean wantMen = true ; 
   		ArrayList<Player> men = this.mySqlDatabase.fetchAllPlayers(wantMen) ;
   		wantMen = false ; 
   		ArrayList<Player> women = this.mySqlDatabase.fetchAllPlayers(wantMen) ;
   		ArrayList<Team> teams = this.mySqlDatabase.fetchTeams(this.selectedLeague) ;
   		this.viewFX.setCreateTeamsPaneData(men, women, teams) ; 
	}

	public void displayPopup(String title, String message) {
		this.viewFX.popupWindow(title, message) ;
	}

	public void submitNewTeam(Team team) {
		if(team == null) {
			String title = "Unable to Save" ;
			String message = "Team is missing. Did you add it? Did you select it in the team section? " ;
			this.viewFX.popupWindow(title, message) ;
			return ; 
		}
		if(team.isMissingDivisionName() && ! this.selectedLeague.isMissingDivisionName() ) {
			team.setDivisionName(this.selectedLeague.getDivisionName()) ;
		}
		boolean success = this.mySqlDatabase.insertNewTeam(team) ;
		String title = "Save Failed" ;
		String message = "Unable to save " + team.getTeamName() ;
		if(success) {
			title = "Save Comlete!" ; 
			message = "Saved team " + team.getTeamName() ; 
		}
		this.viewFX.popupWindow(title, message) ;
	}

	public void displayUpdateDivisionsPane() {
		this.setFlagForScreen("Update Divisions") ;
   		this.viewFX.setUpdateDivisionsPane() ;
   		// Prepare and pass along any data needed to start. Probably the 2 week standings for the current league.
	}

	public void setMultipleMatchDates(ObservableList<String> dates) {
		if(isUpdateDivisionsScreen) {
			ArrayList<Team> teams = this.mySqlDatabase.fetchTeams(this.selectedLeague) ;
			ArrayList<Match> matches = this.mySqlDatabase.fetchMatches(selectedLeague, dates) ; 
			ArrayList<TeamRecentStandings> teamRecentStandings = computeTeamRecentStandings(teams, matches) ;
			teamRecentStandings.sort(null);
			this.viewFX.updateRecentStandings(teamRecentStandings) ;
		}
	}

	private ArrayList<TeamRecentStandings> computeTeamRecentStandings(ArrayList<Team> teams, ArrayList<Match> matches) {
		ArrayList<TeamRecentStandings> allRecentStandings = new ArrayList<TeamRecentStandings>() ;
		for(int i = 0 ; i < teams.size() ; i++ ) {
			Team team = teams.get(i) ; 
			TeamRecentStandings teamRecentStandings = new TeamRecentStandings(team) ; 
			for(int j = 0 ; j < matches.size() ; j++) {
				Match match = matches.get(j) ; 
				if(team.getTeamName().equals(match.getTeamAName())) {
					int wins = match.getTeamAWins() ; 
					int losses = match.getTeamBWins() ; 
					teamRecentStandings.addMatchResult(wins, losses) ; 
				}
				if(team.getTeamName().equals(match.getTeamBName())) {
					int wins = match.getTeamBWins() ; 
					int losses = match.getTeamAWins() ; 
					teamRecentStandings.addMatchResult(wins, losses) ; 
				}
			} // end of for match(j) loop
			allRecentStandings.add(teamRecentStandings) ;
		} // end of for team(i) loop
		return allRecentStandings;
	}

	public void saveNewDivisionForTeam(Team team) {
		this.mySqlDatabase.updateDivisionOfTeam(team) ;
	}

	public ArrayList<String> getTeamMatchCount() {
		ArrayList<String> teamCountLabels = new ArrayList<String>() ;
		if(this.isNewTeamList) {
			this.workingTeamNames = fetchTeamList(selectedLeague) ;
			this.isNewTeamList = false ;
		}
		for(int i = 0 ; i < this.workingTeamNames.length ; i++) {
			String teamName = this.workingTeamNames[i] ; 
			int count = 0 ; 
			for(int j = 0 ; j < this.selectedMatches.size() ; j++) {
				Match match = this.selectedMatches.get(j) ; 
				if(teamName.equals(match.getTeamAName()) || teamName.equals(match.getTeamBName())) {
					count++ ; 
				}
			}
			teamCountLabels.add(count + " " + teamName) ;
		}
		
		return teamCountLabels ;
	}

}
