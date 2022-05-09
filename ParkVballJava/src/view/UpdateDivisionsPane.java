package view;

import java.util.ArrayList;

import controller.Controller;
import controller.TeamRecentStandings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class UpdateDivisionsPane extends BorderPane {
	private Controller controller ; 
	private LeagueChoosePane leagueChoosePane ; 
	private UpdateDivisionsAllTeamsGridPane allTeamsGrid ;
	
	public UpdateDivisionsPane(Controller controller) {
		this.controller = controller ; 
		
		init() ; 
		addLeaguePane() ;
		addBottomPane() ; 
		addDivisionChooseRegion() ; 
	}
	
	private void addDivisionChooseRegion() {
		BorderPane divisionChooseRegion = new BorderPane() ; 
		
		GridPane teamHistoriesGrid = new GridPane() ; 
		
		divisionChooseRegion.setBottom(teamHistoriesGrid);
		divisionChooseRegion.setCenter(allTeamsGrid);
		this.setCenter(divisionChooseRegion);
	}

	private void init() {
		this.allTeamsGrid = new UpdateDivisionsAllTeamsGridPane(this.controller) ;
		
	}

	private void addLeaguePane() {
		this.leagueChoosePane = new LeagueChoosePane(controller, controller.getSelectedLeague()) ;
		this.leagueChoosePane.removeDivisionSection() ; 
		this.leagueChoosePane.makeDatesMultiselect() ; 
		this.leagueChoosePane.setLeague(this.controller.getSelectedLeague()) ;
		this.setTop(leagueChoosePane) ;
	}

	private void addBottomPane() {
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setHgap(30);

		Button teamRecordButton = new Button("Team Records");
		teamRecordButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> teamRecordEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayTeamRecord();
			}
		};
		teamRecordButton.addEventFilter(MouseEvent.MOUSE_CLICKED, teamRecordEvent);

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
		bottomPane.add(teamRecordButton, x, y);
		x++ ; 
		bottomPane.add(cancelButton, x, y) ; 
		this.setBottom(bottomPane) ; 
	}

	public void setNewMatchDates(ObservableList<String> matchDates) {
		this.leagueChoosePane.setNewMatchDates(matchDates);
	}

	public void setTeamRecentStandings(ArrayList<TeamRecentStandings> teamRecentStandings) {
		this.allTeamsGrid.setTeamRecentStandings(teamRecentStandings) ; 
	}

}
