package view;

import controller.Controller;
import controller.Match;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class UpdateResultsSingleMatchPane extends GridPaneControlled {
	
	Controller controller ; 
	Match match ; 
	TextField teamAWins ; 
	TextField teamBWins ; 
	
	UpdateResultsSingleMatchPane(Controller controller, Match match) {

		this.controller = controller ; 
		this.match = match ; 
		
		this.setPadding(new Insets(0, 0, 0, 0));
		this.setVgap(0);
		this.setHgap(5);
		
		Label teamALabel = new Label(String.format("%-15s",match.getTeamAName())) ; 
		teamALabel.setMinWidth(80) ;
		teamALabel = colorTeamByDivision(teamALabel, match.getDivisionName()) ;
		this.teamAWins = new TextField() ;
		this.teamAWins.setMaxWidth(25) ; 
		this.teamAWins.setText("" + this.match.getTeamAWins());
		Label teamBLabel = new Label(String.format("%-15s",match.getTeamBName())) ; 
		teamBLabel.setMinWidth(80) ;
		teamBLabel = colorTeamByDivision(teamBLabel, match.getDivisionName()) ;
		this.teamBWins = new TextField() ; 
		this.teamBWins.setMaxWidth(25) ;
		this.teamBWins.setText("" + this.match.getTeamBWins());
		
		Button submitButton = new Button("Submit") ; 
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> addWinsEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				match.setTeamAWins(extractWins(teamAWins));
				match.setTeamBWins(extractWins(teamBWins)) ;
				controller.addWinsEventOneMatch(match);
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, addWinsEvent);

		Button removeButton = new Button("Remove") ; 
		removeButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> removeMatchEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.deleteSingleMatch(match);
			}
		};
		removeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, removeMatchEvent);
		
		int x = 0 ; 
		this.add(teamALabel, x, 0) ; 
		x++ ; 
		this.add(teamAWins, x, 0) ; 
		x++ ; 
		x++ ;
		this.add(teamBLabel, x, 0) ; 
		x++ ; 
		this.add(teamBWins, x, 0) ; 
		x++ ;
		x++ ; 
//		this.add(editTeamButton, x, 0) ; Decided to do this a different way 
		x++ ; 
		this.add(submitButton, x, 0) ; 
		x++ ; 
		this.add(removeButton, x, 0) ; 
		x++ ; 
	}

	private Label colorTeamByDivision(Label label, String divisionName) {
		if("green".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.GREEN) ;
		}
		else if("blue".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.BLUE) ;
		}
		else if("red".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.RED) ;
		}
		return label ; 
	}
	
	private int extractWins(TextField textField) {
		String winsString = textField.getText() ;
		int wins = 0 ; 
		if(winsString == null || winsString.isEmpty()) {
			wins = 0 ;
		}
		else {
			try {
				wins = Integer.parseInt(winsString) ;
			}
			catch (NumberFormatException exception) {
				wins = 0 ;
			}
		}
		return wins ; 
	}
	
	public int getMatchId() {
		return this.match.getId() ; 
	}

	public Match getMatch() {
		match.setTeamAWins(extractWins(teamAWins));
		match.setTeamBWins(extractWins(teamBWins)) ;
		return this.match ;
	}
}
