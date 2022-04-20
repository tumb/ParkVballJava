package view;

import controller.Controller;
import controller.League;
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

public class PaneFactory {

	private static PaneFactory paneFactory ; 
	private League league;

	private ListView<String> dayOfWeekList ;
	private ListView<String> divisionCount ;

	GridPaneControlled leaguePane ;
	GridPaneControlled homePane ; 
	GridPaneControlled schedulePane ;
	// Used for schedulePane
	ListView<String> teamAList  ; 
	ListView<String> teamBList  ; 

	private static Controller controller ; 

	public static PaneFactory getPaneFactory() {
		if(paneFactory == null) {
			paneFactory = new  PaneFactory() ; 
		}
		paneFactory.init() ; 
		return paneFactory ; 
	}
	
	public static void setController(Controller controller) {
		PaneFactory.controller = controller ; 
	}
	
	private void init() {
		this.dayOfWeekList = new ListView<String>() ;
		this.divisionCount = new ListView<String>() ;
	}
	
	public void fillDayList() {
		this.dayOfWeekList = new ListView<String>() ;
		ObservableList<String> dayList = FXCollections.observableArrayList(PaneFactory.controller.fetchDayList());
		this.dayOfWeekList.setItems(dayList);
		ChangeListener<String> dayChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue ) {
				controller.setLeagueDay(newValue) ; 
			} 
		} ;
		dayOfWeekList.getSelectionModel().selectedItemProperty().addListener(dayChange) ; 
		this.dayOfWeekList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST) ;
	}

	public void fillDivisionCountList() {
		ObservableList<String> divisionList = FXCollections.observableArrayList("1", "2", "3");
		this.divisionCount.setItems(divisionList);
		this.divisionCount.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST) ;
	}

	public League getLeague() {
		return this.league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	private void buildHomePane() {
		homePane = new GridPaneControlled();
		homePane.setController(PaneFactory.controller) ; 
		homePane.setPadding(new Insets(10, 10, 10, 10));
		homePane.setVgap(20);
		homePane.setHgap(5);
		
		Label yearLabel = new Label("Year") ;
		ListView<String> yearList = new ListView<String>() ; 
		yearList.setItems(controller.buildYearList()) ;
		yearList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST) ; 
		
		Label dayLabel = new Label("Day of Week") ;
		fillDayList() ;
		
		
		Button standingsButton = new Button("Standings") ;
		Button addResultsButton = new Button("Update Results") ;
		Button scheduleButton = new Button("Make Schedule") ; 
		Button modifyLeague = new Button("Modify League") ;
		Button createLeague = new Button("Create League") ; 
		Button displayScheduleButton = new Button("Display Schedule") ; 
		Button teamRecordButton = new Button("Team Record") ;
		
		EventHandler<MouseEvent> teamRecordEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayTeamRecord() ; 
			}
		} ;
		teamRecordButton.addEventFilter(MouseEvent.MOUSE_CLICKED, teamRecordEvent) ;
		
		EventHandler<MouseEvent> displayScheduleEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displaySchedule() ; 
			}
		} ;
		displayScheduleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, displayScheduleEvent) ;
		
		EventHandler<MouseEvent> createLeagueEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.createLeaguePressed() ; 
			}
		} ;
		createLeague.addEventFilter(MouseEvent.MOUSE_CLICKED, createLeagueEvent) ;
		
		EventHandler<MouseEvent> makeScheduleEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.makeSchedule() ; 
			}
		} ;
		scheduleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, makeScheduleEvent) ;
		
		EventHandler<MouseEvent> updateResultsEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.updateResults() ; 
			}
		} ;
		addResultsButton.addEventFilter(MouseEvent.MOUSE_CLICKED, updateResultsEvent) ;
		
		EventHandler<MouseEvent> stadingsEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayStandings() ; 
			}
		} ;
		standingsButton.addEventFilter(MouseEvent.MOUSE_CLICKED, stadingsEvent) ;
		
		int y = 0 ;
		homePane.add(yearLabel, 0, y) ;
		homePane.add(dayLabel, 1, y) ; 
		y++ ; 
		homePane.add(yearList, 0, y) ;
		homePane.add(this.dayOfWeekList, 1, y) ;
		y++ ; 
		homePane.add(displayScheduleButton, 0, y) ;
		y++ ;
		homePane.add(standingsButton, 0, y) ;
		y++ ;
		homePane.add(teamRecordButton, 0, y); 
		y++ ; 
		homePane.add(addResultsButton, 0, y) ;
		y++ ;
		homePane.add(scheduleButton, 0, y) ;
		y++ ;
		homePane.add(modifyLeague, 0, y) ;
		y++ ;
		homePane.add(createLeague, 0, y) ;
		y++ ;
		
	}
	
	private void buildLeaguePane() {
		leaguePane = new GridPaneControlled();
		leaguePane.setPadding(new Insets(10, 10, 10, 10));
		leaguePane.setVgap(20);
		leaguePane.setHgap(5);

		Label yearLabel = new Label("Year");
		Label dayLabel = new Label("Day of Week");
		Label divisionLabel = new Label("How many divisions");
		Button leagueSubmitButton;
		Button cancelButton;
		TextField yearField;


		fillDayList();
		fillDivisionCountList() ; 
		yearField = new TextField() ;
		leagueSubmitButton = new Button("Submit") ;
		EventHandler<MouseEvent> leagueSubmitEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.leagueSubmitButtonPress() ; 
			}
		} ;
		leagueSubmitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, leagueSubmitEvent) ;

		cancelButton = new Button("Cancel") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane() ; 
			}
		} ;
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent) ;
		
		int y = 0;
		leaguePane.add(yearLabel, 0, y);
		leaguePane.add(dayLabel, 1, y);
		leaguePane.add(divisionLabel, 2, y);
		y++;
		leaguePane.add(yearField, 0, y);
		leaguePane.add(this.dayOfWeekList, 1, y);
		leaguePane.add(this.divisionCount, 2, y);
		y++;
		leaguePane.add(leagueSubmitButton, 0, y);
		leaguePane.add(cancelButton, 1, y);
		y++;
		
	}
	
	public GridPaneControlled getLeaguePane() {
		if(this.leaguePane == null) {
			buildLeaguePane() ; 
		}
		return this.leaguePane ; 
	}
	
	public GridPaneControlled getHomePane() {
		if(this.homePane == null) {
			buildHomePane() ; 
		}
		return this.homePane ; 
	}
	
}
