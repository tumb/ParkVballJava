package view;

import java.util.ArrayList;

import controller.Controller;
import controller.League;
import controller.Match;
import controller.Player;
import controller.Team;
import controller.TeamRecentStandings;
import controller.TeamStandings;
import javafx.application.Application ;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ApplicationFX extends Application {

	public static int MAX_HEIGHT_OF_SHORT_LIST = 75 ; 
	
	private Controller controller;
	private Group homeGroup ; 
	private Scene baseScene ; 
	private PaneFactory paneFactory ; 
	private GridPaneControlled createLeaguePane ; 
	private GridPaneControlled homePane ; 
	private SchedulePane schedulePane ; 
	private ResultsPane resultsPane ; 
	private StandingsPane standingsPane ; 
	private DisplaySchedulePane displaySchedulePane ; 
	private TeamRecordDisplayPane teamRecordPane ; 
	private AddPlayersPane addPlayersPane ; 
	private TeamCreationPane createTeamsPane ; 
	private UpdateDivisionsPane updateDivisionsPane ; 
	private ChangeDatesPane changeDatesPane ; 
	private ScrollPane scrollPane ; 
	
	public static void main(String[] arguments){
		launch(arguments) ;
	}
	
	@Override
	public void start(Stage primaryStage) {
		paneFactory = PaneFactory.getPaneFactory() ; 
		controller.setView(this) ; 
		PaneFactory.setController(controller) ;

		homeGroup = new Group() ; 
		baseScene = new Scene(homeGroup, 800, 600) ;
		this.homePane = paneFactory.getHomePane() ;
		this.scrollPane = new ScrollPane() ; 
		this.scrollPane.setContent(this.homePane);
		baseScene.setRoot(this.scrollPane) ; 
		baseScene.setFill(Color.CYAN) ;
		primaryStage.setScene(baseScene) ;
		primaryStage.setTitle("Delaware Park Doubles") ; 
		
		primaryStage.show() ; 
	}
	
	@Override
	public void init() {
		controller = new Controller() ; 
		controller.init() ; 
		this.schedulePane = new SchedulePane(this.controller) ;
	}
	
	public void setCreateLeagueScene() {
		createLeaguePane = paneFactory.getLeaguePane() ;
		createLeaguePane.setController(this.controller) ;
		this.scrollPane.setContent(createLeaguePane);
		// stage.setScene(leagueScene) ;

	}

	public void setMakeSchedulePane() {
		if(this.schedulePane == null) {
			this.schedulePane = new SchedulePane(this.controller) ;
		}
		this.scrollPane.setContent(schedulePane);
	}
	
	public void returnToHomePane() {
		this.scrollPane.setContent(this.homePane) ;
	}

	public void updatesTeamsForScheduling(String[] teamNamesForScheduling) {
		this.schedulePane.updateTeamnames(teamNamesForScheduling) ; 
	}

	public void setOpponentsDisplayInScheduling(String[] opponentsDisplay, String team) {
		this.schedulePane.setOpponentsDisplay(opponentsDisplay) ;
		this.schedulePane.updateOpponentsLabel(team) ;
	}

	public Match getInputsToSelectMatch(League selectedLeague, String matchDate) {
		return this.schedulePane.getInputsToSelectMatch(selectedLeague, matchDate) ;
	}
	
	public String getCurrentlySelectedMatchFromScheduling() {
		return this.schedulePane.getCurrentlySelectedMatchFromScheduling() ;
	}

	public void updateSchedulingTeamLists(ArrayList<Match> selectedMatches, String teamAName, String teamBName) {
		this.schedulePane.updateSelectedMatches(selectedMatches) ; 
		// remove teamA from teamAList and teamB from teamBList
		this.schedulePane.removeFromTeamList(teamAName, true) ;
		this.schedulePane.removeFromTeamList(teamBName, false) ;
	}

	public boolean popupWindow(String title, String message) {
		BorderPane borderPane = new BorderPane() ; 
		Scene scene = new Scene(borderPane, 200, 200) ;
		Stage stage = new Stage() ; 
		stage.setScene(scene) ;
		stage.setTitle(title) ;
		Label label = new Label(message) ;
		Button okButton = new Button("OK") ; 
		EventHandler<MouseEvent> okEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.close() ;
			}
		};
		okButton.addEventFilter(MouseEvent.MOUSE_CLICKED, okEvent);

		borderPane.setCenter(label) ;
		BorderPane bottomPane = new BorderPane() ; 
		bottomPane.setCenter(okButton) ; 
		borderPane.setBottom(bottomPane) ;
		
		stage.show() ; 
		return true ;
	}

	public void setUpdateResultsPane(ArrayList<Match> matches) {
		if(this.resultsPane == null) {
			this.resultsPane = new ResultsPane(this.controller) ;
		}
		
		resultsPane.setMatches(matches) ; 
		this.scrollPane.setContent(this.resultsPane) ; 
	}

	public void setStandingsPane(ArrayList<TeamStandings> teamStandings ) {
		if(this.standingsPane == null) {
			this.standingsPane = new StandingsPane(this.controller) ;
		}
		this.standingsPane.setStandings(teamStandings) ; 
		this.scrollPane.setContent(this.standingsPane) ; 
	}
	
	public void setNewMatchDates(ObservableList<String> matchDates) {
		if(this.displaySchedulePane != null) {
			(this.displaySchedulePane).setNewMatchDates(matchDates) ; 
		}
		if(this.resultsPane != null) {
			this.resultsPane.setNewMatchDates(matchDates) ;
		}
		if(this.updateDivisionsPane != null) {
			this.updateDivisionsPane.setNewMatchDates(matchDates) ;
		}
	}

	public void updateScheduledMatchesView(ArrayList<Match> selectedMatches) {
		this.schedulePane.updateSelectedMatches(selectedMatches) ;
	}

	public void updateSchedulingAfterMatchRemoved(ArrayList<Match> selectedMatches, Match matchToRemove) {
		this.schedulePane.updateSchedulingAfterMatchRemoved(selectedMatches, matchToRemove) ;
	}

	public void displayTeamNoShowPane(League league, String matchDate) {
		TeamNoShowPane teamNoShowPane = new TeamNoShowPane(this.controller, league, matchDate) ;
		this.scrollPane.setContent(teamNoShowPane) ;
	}

	public void removeSingleResultPane(Match match) {
		this.resultsPane.removeSingleResultPane(match) ;
	}

	public void setDisplaySchedulePane(ArrayList<Match> matches) {
		if(this.displaySchedulePane == null) {
			this.displaySchedulePane = new DisplaySchedulePane(this.controller) ;
		}
		this.displaySchedulePane.setSchedule(matches) ; 
		this.scrollPane.setContent(this.displaySchedulePane) ; 
	}

	public void setTeamRecordPane(ObservableList<String> teams) {
		if(this.teamRecordPane == null) {
			this.teamRecordPane = new TeamRecordDisplayPane(this.controller) ;
		}
		this.teamRecordPane.setTeamsToSelect(teams) ;
		this.scrollPane.setContent(this.teamRecordPane) ; 
	}

	public void setTeamRecord(Match[] teamMatches, String teamName) {
		this.teamRecordPane.setTeamMatches(teamMatches, teamName) ; 
	}

	public void setAddPlayersPane(ObservableList<String> existingPlayers) {
		if(this.addPlayersPane == null) {
			this.addPlayersPane = new AddPlayersPane(this.controller) ;
		}
		this.addPlayersPane.setPlayers(existingPlayers) ;
		this.scrollPane.setContent(this.addPlayersPane) ; 
		
	}

	public void addToAllPlayersList(Player player) {
		this.addPlayersPane.addToAllPlayersList(player) ;
	}

	public void setCreateTeamsPane() {
		if(this.createTeamsPane == null) {
			this.createTeamsPane = new TeamCreationPane(this.controller) ;
		}
		this.scrollPane.setContent(this.createTeamsPane) ; 
	}

	public void setCreateTeamsPaneData(ArrayList<Player> men, ArrayList<Player> women, ArrayList<Team> teams) {
		this.createTeamsPane.setData(men, women, teams); 
	}

	public void setUpdateDivisionsPane() {
		if(this.updateDivisionsPane == null) {
			this.updateDivisionsPane = new UpdateDivisionsPane(this.controller) ;
		}
		this.scrollPane.setContent(this.updateDivisionsPane) ; 
	}

	public void updateRecentStandings(ArrayList<TeamRecentStandings> teamRecentStandings) {
		this.updateDivisionsPane.setTeamRecentStandings(teamRecentStandings) ; 
	}

	public void updateLeagueTeamList(ArrayList<Team> teams) {
		this.createTeamsPane.updateLeagueTeamList(teams) ;
	}

	public void displayChangeScheduleDatePane(String[] existingDates) {
		if(this.changeDatesPane == null) {
			this.changeDatesPane = new ChangeDatesPane(this.controller) ;
		}
		this.changeDatesPane.setExistingDates(existingDates) ;
		this.scrollPane.setContent(this.changeDatesPane) ; 
	}

	public void setDefaults(League selectedLeague, String matchDate) {
		if(this.schedulePane != null) {
			schedulePane.setDefaults(selectedLeague, matchDate) ; 
		}
	}

	public void updateDivisionsForScheduling(ObservableList<String> divisionNameList) {
		this.schedulePane.setDivisionNames(divisionNameList) ; 
	}
	
	// A better popup would be like this:
//	@Override
//	public void start(final Stage primaryStage) {
//	    Button btn = new Button();
//	    btn.setText("Open Dialog");
//	    btn.setOnAction(
//	        new EventHandler<ActionEvent>() {
//	            @Override
//	            public void handle(ActionEvent event) {
//	                final Stage dialog = new Stage();
//	                dialog.initModality(Modality.APPLICATION_MODAL);
//	                dialog.initOwner(primaryStage);
//	                VBox dialogVbox = new VBox(20);
//	                dialogVbox.getChildren().add(new Text("This is a Dialog"));
//	                Scene dialogScene = new Scene(dialogVbox, 300, 200);
//	                dialog.setScene(dialogScene);
//	                dialog.show();
//	            }
//	         });
//	    }
	
	
}
