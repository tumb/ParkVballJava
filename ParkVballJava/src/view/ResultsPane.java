package view;

import java.util.ArrayList;

import controller.Controller;
import controller.Match;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class ResultsPane extends BorderPane {
	private Controller controller ;
	private LeagueChoosePane leagueChoosePane ; 
	private UpdateResultsMatchGrid matchGridPane ; 
	
	public ResultsPane(Controller controller) {
		this.controller = controller ;
		this.leagueChoosePane = this.controller.getLeagueChoosePane() ;
		this.setTop(leagueChoosePane) ;
		this.matchGridPane = new UpdateResultsMatchGrid(controller) ;
		this.setCenter(matchGridPane) ; 
		// Create and set the bottom pane for buttons Submit All and Cancel
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setVgap(20);
		bottomPane.setHgap(50);

		
		Button noShowButton = new Button("Team Didn't Play");
		noShowButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> noShowEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.displayNoShowPane();
			}
		};
		noShowButton.addEventFilter(MouseEvent.MOUSE_CLICKED, noShowEvent);
		

		Button submitAllButton = new Button("Submit All") ; 
		submitAllButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitAllEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				ArrayList<Match> allMatches = matchGridPane.getAllDisplayedMatches() ; 
				controller.submitAllResults(allMatches);
			}
		};
		submitAllButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitAllEvent);

		Button cancelButton = new Button("Cancel");
		cancelButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane();
			}
		};
		cancelButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);

		bottomPane.add(noShowButton, 0, 0) ; 
		bottomPane.add(submitAllButton, 1, 0) ; 
		bottomPane.add(cancelButton, 2, 0) ; 
		this.setBottom(bottomPane) ; 
		
	}

	public void setMatches(ArrayList<Match> matches) {
		this.matchGridPane.setAllMatches(matches) ;		
	}

	public void setNewMatchDates(ObservableList<String> matchDates) {
		this.leagueChoosePane.setNewMatchDates(matchDates) ;
	}

	public void removeSingleResultPane(Match match) {
		this.matchGridPane.removeSingleResultPane(match) ;
	}

}
