package view;

import java.util.ArrayList;

import controller.Controller;
import controller.League;
import controller.Match;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class TeamNoShowPane extends GridPaneControlled {

	Controller controller ; 
	League league ; 
	String matchDate ;
	String teamName ; 
	ArrayList<Match> matches = new ArrayList<Match>() ;
	ListView<String> matchList = new ListView<String>() ;
	
	public TeamNoShowPane(Controller controller, League league, String matchDate) {
		this.controller = controller;
		this.league = league ; 
		this.matchDate = matchDate ; 
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(20);
		this.setHgap(5);

		ListView<String> teamList = new ListView<String>() ;
		teamList.setMaxHeight(4*ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		teamList.setMinHeight(2*ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		String[] teams = this.controller.fetchTeamList(league) ; 
		ObservableList<String> allTeamNames = FXCollections.observableArrayList(teams);
		teamList.setItems(allTeamNames);
		ChangeListener<String> teamChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				teamName = newValue ;
				displayMatches(teamName) ;
			}
		};
		teamList.getSelectionModel().selectedItemProperty().addListener(teamChange) ;

		matchList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		matchList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		
		// Set top row
		int x = 0 ; 
		int y = 0 ; 
		this.add(new Label(matchDate), x, 0);
		x++ ;
		this.add(new Label(league.getDayOfWeek()), x, 0);
		x++ ;
		this.add(new Label(league.getDivisionName()), x, 0);
		x++ ;
		this.add(new Label("" + league.getYear()), x, 0);
		// End of top row
		x = 0 ; 
		y++ ; 
		this.add(new Label("Teams"), x, y);
		y++ ; 
		this.add(teamList, x, y);
		y++ ; 
		this.add(new Label("Match List"), x, y);
		y++ ; 
		this.add(matchList, x, y);
		
		y++ ; 
		Button mergeButton = new Button("Merge and Save Matches");
		mergeButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> mergeEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.mergeMatches(matches, teamName) ;
				controller.updateResults() ;
			}
		};
		mergeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mergeEvent);
		this.add(mergeButton, x, y) ;
		
		x++ ;
		Button cancelButton = new Button("Exit (Does not Save)");
		cancelButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.updateResults() ;
			}
		};
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);
		this.add(cancelButton, x, y) ;

	}

	private void displayMatches(String teamName) {
		// Fetch matches for this team, league, date 
		this.matches = this.controller.fetchMatchesForTeam(this.league, this.matchDate, this.teamName) ;
		String[] listStrings = new String[this.matches.size()] ;
		// Display and enable button to merge matches
		for(int i = 0 ; i < matches.size() ; i++) {
			String matchString = matches.get(i).toString();
			listStrings[i] = matchString ; 
		}
		ObservableList<String> list = FXCollections.observableArrayList(listStrings) ; 
		matchList.setItems(list);
	}
}
