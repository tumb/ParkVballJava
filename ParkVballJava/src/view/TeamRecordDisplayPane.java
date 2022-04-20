package view;

import controller.Controller;
import controller.Match;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class TeamRecordDisplayPane extends BorderPane {
	Controller controller ; 
	GridPaneControlled teamSelectionPane ;
	GridPaneControlled recordDisplayPane ;
	GridPaneControlled bottomButtonPane ; 
	ListView<String> teamList ;
	String teamName = new String("") ; 
	Match[] matches = new Match[0] ; 
	
	TeamRecordDisplayPane(Controller controller) {
		this.controller = controller ; 
		this.teamSelectionPane = makeTeamSelectionPane() ;
		this.recordDisplayPane = makeRecordDisplayPane() ;
		this.setHeadingLabels() ;
		this.bottomButtonPane  = makeBottomButtonPane () ;
		
		this.setTop(teamSelectionPane) ;
		this.setCenter(this.recordDisplayPane) ; 
		this.setBottom(bottomButtonPane) ;
	}

	private GridPaneControlled makeBottomButtonPane() {
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setVgap(20);
		bottomPane.setHgap(50);

		Button cancelButton = new Button("Exit");
		cancelButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane();
			}
		};
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);

		bottomPane.add(cancelButton, 1, 0) ; 
		return bottomPane ;
	}

	private GridPaneControlled makeRecordDisplayPane() {
		GridPaneControlled teamRecordPane = new GridPaneControlled() ; 
		teamRecordPane.setController(controller) ; 
		teamRecordPane.setPadding(new Insets(10, 10, 10, 10));
		teamRecordPane.setVgap(5);
		teamRecordPane.setHgap(20);

		return teamRecordPane ; 
	}

	private void setHeadingLabels() {
		int y = 0 ; 
		if(!(this.teamName == null || this.teamName.isEmpty())) {
			recordDisplayPane.add(new Label(teamName + " opponents"), 0, y);
			y++ ;
		}
		int x = 0 ; 
		this.recordDisplayPane.add(new Label("Team"), x, y) ;
		x++ ; 
		this.recordDisplayPane.add(new Label("Won ?"), x, y) ;
		x++ ; 
		this.recordDisplayPane.add(new Label("Date played"), x, y);
	}

	private GridPaneControlled makeTeamSelectionPane() {
		GridPaneControlled selectionPane = new GridPaneControlled() ; 
		selectionPane.setController(controller) ; 
		selectionPane.setPadding(new Insets(10, 10, 10, 10));
		selectionPane.setVgap(5);
		selectionPane.setHgap(20);

		int x = 0 ; 
		int y = 0 ; 
		selectionPane.add(new Label("Team"), x, y) ;
		x++ ; 
		selectionPane.add(new Label("Result"), x, y) ;
		x++ ; 
		selectionPane.add(new Label("Date"), x, y) ;
		
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

		teamList = new ListView<String>();
		teamList.setItems(controller.buildTeamList());
		ChangeListener<String> teamChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setTeamRecord(newValue);
			}
		};
		teamList.getSelectionModel().selectedItemProperty().addListener(teamChange) ;
		teamList.setMaxHeight(3 * ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		teamList.setMinHeight(3 * ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		x = 0 ;
		y++ ; 
		selectionPane.add(teamList, x, y) ;
		x++ ;
		selectionPane.add(dayList, x, y) ;
		x++ ; 
		selectionPane.add(yearList, x, y) ;
		
		
		return selectionPane ;
	}

	public void setTeamsToSelect(ObservableList<String> teams) {
		this.teamList.getItems().clear(); 
		this.teamList.setItems(teams);
	}

	public void setTeamMatches(Match[] teamMatches, String teamName2) {
		this.recordDisplayPane.getChildren().clear();
		this.teamName = teamName2 ; 
		this.matches = teamMatches ; 
		this.setHeadingLabels() ;
		int x = 0 ; 
		int y = 2 ;
		for(int i = 0 ; i < teamMatches.length ; i++) {
			Match match = teamMatches[i] ;
			boolean isTeamA = this.teamName.equals(match.getTeamAName()) ;
			String opponentName = match.getTeamAName() ;
			int wins = match.getTeamBWins() ;
			int opponentWins = match.getTeamAWins() ; 
			Label dateLabel = new Label(match.getDate()) ;
			if(isTeamA) {
				opponentName = match.getTeamBName(); 
				wins = match.getTeamAWins() ;
				opponentWins = match.getTeamBWins() ; 
			}
			Label winsLabel = makeWinsLabel(wins, opponentWins) ;
			Label opponentLabel = new Label(opponentName) ; 
			if("blue".equals(match.getDivisionName())) {
				opponentLabel.setTextFill(Color.BLUE) ;
			}
			else if("green".equals(match.getDivisionName())) {
				opponentLabel.setTextFill(Color.GREEN) ;
			}
			else if("red".equals(match.getDivisionName())) {
				opponentLabel.setTextFill(Color.RED) ;
			}
			this.recordDisplayPane.add(opponentLabel, x, y);
			x++ ;
			this.recordDisplayPane.add(winsLabel, x, y);
			x++ ; 
			this.recordDisplayPane.add(dateLabel, x, y) ;
			x = 0 ; 
			y++ ; 
		}
	}

	private Label makeWinsLabel(int wins, int opponentWins) {
		Label winsLabel = new Label("") ;
		if(wins == 2) {
			winsLabel = new Label("Won") ; 
			winsLabel.setStyle("-fx-background-color: lightgreen; ");
		}
		if(wins == 1 && opponentWins == 1) {
			winsLabel = new Label("Split") ; 
			winsLabel.setStyle("-fx-background-color: yellow; ");
		}
		if(wins == 0 && opponentWins == 2) {
			winsLabel = new Label("Lost") ; 
			winsLabel.setStyle("-fx-background-color: lightpink; ");
		}
		if(wins == 1 && opponentWins == 0) {
			winsLabel = new Label("half win") ;
			winsLabel.setStyle("-fx-background-color: lightgray; ");
		}
		if(wins == 0 && opponentWins == 1) {
			winsLabel = new Label("half loss") ;
			winsLabel.setStyle("-fx-background-color: lightgray; ");
		}
		if(wins == 0 && opponentWins == 0) {
			winsLabel = new Label("unplayed") ;
		}
		return winsLabel ;
	}

}
