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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class SchedulePane extends GridPaneControlled {

	private ListView<String> teamAList;
	private ListView<String> teamBList;
	private ListView<String> opponentsList ; 
	private ListView<String> selectedMatchesList ; 
	private ListView<String> teamCountList ; 
	private Label opponentsLabel ;
	private TextField dateField ; 

	public SchedulePane(Controller controller) {
		this.controller = controller;
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(20);
		this.setHgap(5);

		this.opponentsLabel = new Label("opponents") ;
		this.teamCountList = new ListView<String>() ; 
		
		//################# Set up top section of pane #############################
		Label yearLabel = new Label("Year");
		ListView<String> yearList = new ListView<String>();
		yearList.setItems(controller.buildYearList());
		ChangeListener<String> yearChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setLeagueYear(newValue);
			}
		};
		yearList.getSelectionModel().selectedItemProperty().addListener(yearChange);

		yearList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		yearList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		Label dayLabel = new Label("Day");
		ListView<String> dayList = new ListView<String>();
		dayList.setItems(controller.buildDayList());
		ChangeListener<String> dayChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setLeagueDay(newValue);
			}
		};
		dayList.getSelectionModel().selectedItemProperty().addListener(dayChange) ;
		dayList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		dayList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);


		Label divisionLabel = new Label("Division");
		ListView<String> divisionList = new ListView<String>();
		divisionList.setItems(controller.buildDivisionNameList());
		ChangeListener<String> divisionChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setLeagueDivisionName(newValue);
			}
		};
		divisionList.getSelectionModel().selectedItemProperty().addListener(divisionChange);
		divisionList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		divisionList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		dateField = new TextField() ; 
		ChangeListener<String> dateChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setMatchDate(newValue);
			}
		};
		this.dateField.textProperty().addListener(dateChange) ; 

		//#################### Middle selection section with the lists of teams and matches #################
		
		teamAList = new ListView<String>(); // Will be empty until an update is called by controller
		teamAList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST * 4);
		ChangeListener<String> teamAChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setTeamHistory(newValue);
			}
		};
		teamAList.getSelectionModel().selectedItemProperty().addListener(teamAChange);
		
		teamBList = new ListView<String>();
		teamBList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST * 4);
		
		selectedMatchesList = new ListView<String>();
		selectedMatchesList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST * 4);
		
		this.opponentsList = new ListView<String>();

		Button addMatchButton = new Button("Add Match");
		addMatchButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> addMatchEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.addMatchToSelections();
				updateTeamMatchCount() ;
			}

		};
		addMatchButton.addEventFilter(MouseEvent.MOUSE_CLICKED, addMatchEvent);
		
		Button removeMatch = new Button("Remove Match") ;
		removeMatch.setStyle("-fx-background-color: lightblue; ") ; 
		EventHandler<MouseEvent> removeMatchEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.removeMatchFromSelections();
				updateTeamMatchCount() ;
			}
		};
		removeMatch.addEventFilter(MouseEvent.MOUSE_CLICKED, removeMatchEvent);
		
		Button deleteMatch = new Button("Delete Match") ;
		deleteMatch.setStyle("-fx-background-color: pink; ") ; 
		EventHandler<MouseEvent> deleteMatchEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				deleteMatchFromDatabase();
				updateTeamMatchCount() ;
			}
		};
		deleteMatch.addEventFilter(MouseEvent.MOUSE_CLICKED, deleteMatchEvent);
		
		Button submitScheduleButton = new Button("Save All Matches");
		submitScheduleButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.submitSchedule();
			}
		};
		submitScheduleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEvent) ;

		Button changeDateButton = new Button("Change Date (rainout)");
		changeDateButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> changeDateEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayChangeScheduleDatePane();
			}
		};
		changeDateButton.addEventFilter(MouseEvent.MOUSE_CLICKED, changeDateEvent) ;


		Button deleteAllButton = new Button("Delete All From Database");
		deleteAllButton.setStyle("-fx-background-color: pink ; ") ;
		EventHandler<MouseEvent> deleteAllEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.deleteAllMatches();
			}
		};
		deleteAllButton.addEventFilter(MouseEvent.MOUSE_CLICKED, deleteAllEvent) ;

		Button cancelButton = new Button("Cancel");
		cancelButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane();
			}
		};
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);

		int y = 0;
		this.add(divisionLabel, 1, y);
		this.add(yearLabel, 2, y);
		this.add(dayLabel, 0, y);
		this.add(new Label("Date (yyyy-mm-dd)"), 3, y) ;
		y++;
		this.add(yearList, 2, y);
		this.add(dayList, 0, y);
		this.add(divisionList, 1, y);
		this.add(this.dateField, 3, y) ;
		y++;

		int matchY = y;
		
		GridPane buttonPane = new GridPane() ;
		buttonPane.setPadding(new Insets(10, 10, 10, 10));
		buttonPane.setVgap(10);
		buttonPane.setHgap(5);
		int z = 0 ; 
		buttonPane.add(addMatchButton, 0, z);
		z++ ; 
		buttonPane.add(removeMatch, 0, z) ;
		z++ ; 
		buttonPane.add(deleteMatch, 0, z) ;
		z++ ; 
		buttonPane.add(changeDateButton, 0, z);
		z++ ;
		buttonPane.add(submitScheduleButton, 0, z);
		z++ ;
		buttonPane.add(deleteAllButton, 0, z);
		z++ ;
		buttonPane.add(cancelButton, 0, z);

		// Labels
		this.add(new Label("Team A"), 0, matchY);
		this.add(new Label("Team B"), 1, matchY);
		this.add(new Label("Matches"), 3, matchY);
		y = matchY + 1;

		this.add(teamAList, 0, y);
		this.add(teamBList, 1, y);
		this.add(buttonPane, 2, y) ;
		this.add(selectedMatchesList, 3, y);

		y = matchY + 4;
		this.add(this.opponentsLabel, 0, y);
		this.add(new Label("Count (saved or not)"), 3, y);
		y++;
		this.add(opponentsList, 0, y);
		this.add(teamCountList, 3, y);
	}

	public void updateTeamnames(String[] teamNamesForScheduling) {
		ObservableList<String> allTeamNames = FXCollections.observableArrayList(teamNamesForScheduling);
		this.teamAList.setItems(allTeamNames);
		ObservableList<String> teamBNames = FXCollections.observableArrayList(teamNamesForScheduling);
		this.teamBList.setItems(teamBNames);
	}

	public void setOpponentsDisplay(String[] opponentsDisplay) {
		ObservableList<String> allOpponents = FXCollections.observableArrayList(opponentsDisplay);
		this.opponentsList.setItems(allOpponents);
		
	}
	
	public void updateOpponentsLabel(String teamName) {
		String text = "Opponent History" ; 
		if(teamName != null && ! teamName.isEmpty() ) {
			text = teamName + "'s Opponent History" ;
		}
		this.opponentsLabel.setText(text) ;

	}

	public Match getInputsToSelectMatch(League selectedLeague, String matchDate) {
		String teamAName = teamAList.getSelectionModel().selectedItemProperty().getValue() ;
		String teamBName = teamBList.getSelectionModel().selectedItemProperty().getValue() ;
		String divisionName = selectedLeague.getDivisionName() ;
		return new Match(selectedLeague, teamAName, teamBName, matchDate, divisionName) ; 
	}

	public String getCurrentlySelectedMatchFromScheduling() {
		return this.selectedMatchesList.getSelectionModel().selectedItemProperty().getValue() ;
	}
	
	public void updateSelectedMatches(ArrayList<Match> selectedMatches) {
		String[] matchStrings = new String[selectedMatches.size()] ; 
		for(int i = 0 ; i < selectedMatches.size() ; i++) {
			matchStrings[i] = selectedMatches.get(i).toString() ; 
		}
		ObservableList<String> allMatchesObservable = FXCollections.observableArrayList(matchStrings) ; 
		this.selectedMatchesList.getItems().clear() ; 
		this.selectedMatchesList.setItems(allMatchesObservable) ; 
		this.updateTeamMatchCount() ;
	}

	public void removeFromTeamList(String teamName, boolean isTeamA) {
		ListView<String> list = this.teamBList ; 
		if(isTeamA) {
			list = this.teamAList ; 
		}
		ObservableList<String> observableList = list.getItems() ; 
		observableList.removeAll(teamName) ;
	}

	public void updateSchedulingAfterMatchRemoved(ArrayList<Match> selectedMatches, Match matchToRemove) {
		String matchDisplay = matchToRemove.toString(); 
		ObservableList<String> observableList = this.selectedMatchesList.getItems() ; 
		observableList.remove(matchDisplay) ;
		this.teamAList.getItems().add(matchToRemove.getTeamAName()) ;
		this.teamBList.getItems().add(matchToRemove.getTeamBName()) ;
	}
	
	private void updateTeamMatchCount() {
		ArrayList<String> teamMatchCounts = this.controller.getTeamMatchCount() ;
		this.teamCountList.getItems().clear();
		this.teamCountList.getItems().setAll(teamMatchCounts) ;
	}

	private void deleteMatchFromDatabase() {
		String matchAsString = selectedMatchesList.getSelectionModel().getSelectedItem() ;
		Match matchToDelete = new Match(matchAsString) ; 
		boolean success = this.controller.deleteSingleMatch(matchToDelete) ;
		String title = "Deleting Match" ; 
		String message = "Deleted Match " + matchToDelete ; 
		if(!success) {
			message = "Failed to delete " + matchAsString  ;
		}
		this.controller.displayPopup(title, message);
	}
	
}
