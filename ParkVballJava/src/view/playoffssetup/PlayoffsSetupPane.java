package view.playoffssetup;

import java.util.ArrayList;

import controller.Controller;
import controller.TeamStandings;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import view.GridPaneControlled;
import view.LeagueChoosePane;
import view.UpdateDivisionsAllTeamsGridPane;

public class PlayoffsSetupPane extends BorderPane {
	private Controller controller ; 
	private LeagueChoosePane leagueChoosePane ; 
	private PlayoffsSetupAllTeamsGridPane allTeamsGridPane ; 
	
	public PlayoffsSetupPane(Controller controller) {
		this.controller = controller ; 
		
		init() ; 
		addLeaguePane() ;
		addBottomPane() ; 
		addPlayoffsSetupRegion() ; 
	}

	private void init() {
		this.allTeamsGridPane = new PlayoffsSetupAllTeamsGridPane(controller) ; 
	}
	
	private void addPlayoffsSetupRegion() {
		this.setCenter(this.allTeamsGridPane);
	}
	
	private void addBottomPane() {
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setHgap(30);

		// TODO this needs to be written to Save instead of show team record
		Button saveButton = new Button("Save");
		saveButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> saveEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayTeamRecord();
			}
		};
		saveButton.addEventFilter(MouseEvent.MOUSE_CLICKED, saveEvent);

		// TODO this needs to be written to Clear instead of show team record
		Button clearButton = new Button("Clear");
		clearButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> clearEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayTeamRecord();
			}
		};
		clearButton.addEventFilter(MouseEvent.MOUSE_CLICKED, clearEvent);

		Button cancelButton = new Button("Exit");
		cancelButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane();
			}
		};
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);

		int x = 1 ; 
		int y = 0 ; 
		bottomPane.add(saveButton, x, y);
		x++ ; 
		bottomPane.add(clearButton, x, y) ; 
		x++ ; 
		bottomPane.add(cancelButton, x, y) ; 
		this.setBottom(bottomPane) ; 
	}


	
	private void addLeaguePane() {
		this.leagueChoosePane = new LeagueChoosePane(controller, controller.getSelectedLeague()) ;
		this.leagueChoosePane.removeDivisionSection() ; 
		this.leagueChoosePane.setLeague(this.controller.getSelectedLeague()) ;
		this.setTop(leagueChoosePane) ;
	}

	public void updateStandings(ArrayList<TeamStandings> standings) {
		this.allTeamsGridPane.setTeamStandings(standings) ;
	}
	
}
