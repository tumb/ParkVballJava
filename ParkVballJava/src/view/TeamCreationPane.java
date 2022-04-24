package view;

import java.util.ArrayList;

import controller.Controller;
import controller.Player;
import controller.Team;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class TeamCreationPane extends BorderPane {

	private Controller controller ; 
	private ArrayList<Team> teams ; 
	private ArrayList<Player> women ; 
	private ArrayList<Player> men ; 
	private LeagueChoosePane leagueChoosePane ; 
	
	public TeamCreationPane(Controller controller) {
		this.controller = controller ; 
		
		this.setPadding(new Insets(10, 10, 10, 10));

		addBottomPane() ;
		addLeaguePane() ; 
		addTeamEntryPane() ; 

	}
	
	private void addTeamEntryPane() {
		GridPaneControlled teamEntryPane = new GridPaneControlled() ;
		teamEntryPane.setController(controller);
		teamEntryPane.setPadding(new Insets(10, 10, 10, 10));
		teamEntryPane.setVgap(20);
		teamEntryPane.setHgap(5);
		
		int x = 0 ;
		int y = 0 ; 
		Label instructionLabel = new Label("Submit 1 team at a time. Check the team name to prevent duplicates.") ; 
		teamEntryPane.add(instructionLabel, x, y);
		y++ ;

	}

	private void addLeaguePane() {
		this.leagueChoosePane = new LeagueChoosePane(controller, controller.getSelectedLeague()) ;
		this.setTop(leagueChoosePane) ;
	}

	public void addBottomPane() {
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
		this.setBottom(bottomPane) ; 
	}

	
}
