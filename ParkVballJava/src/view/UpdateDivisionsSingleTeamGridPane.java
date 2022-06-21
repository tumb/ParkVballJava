package view;

import controller.Controller;
import controller.TeamRecentStandings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class UpdateDivisionsSingleTeamGridPane extends GridPane{
	
	private Controller controller ; 
	private TeamRecentStandings teamRecentStandings ; 
	private Label teamNameLabel ;
	
	public UpdateDivisionsSingleTeamGridPane(Controller controller, TeamRecentStandings teamRecentStandings) {
		this.controller = controller ; 
		this.teamRecentStandings = teamRecentStandings ; 
		
		this.setHgap(20);
		ColumnConstraints teamNameConstraint = new ColumnConstraints(80) ; 
		ColumnConstraints digitConstraint = new ColumnConstraints(10) ;
		ColumnConstraints divisionsConstraint = new ColumnConstraints(50) ; 

		this.getColumnConstraints().addAll(teamNameConstraint, digitConstraint, digitConstraint, divisionsConstraint) ; 
		
		Label winsLabel = new Label("" + this.teamRecentStandings.getWins()) ;
		Label lossesLabel = new Label("" + this.teamRecentStandings.getLosses()) ;
		teamNameLabel = new Label(this.teamRecentStandings.getTeam().getTeamName()) ; 
		teamNameLabel = setDivisionColor(teamNameLabel) ;
		
		ListView<String> divisionNameChoices = buildDivisionNameChoices() ;
		divisionNameChoices.setMaxHeight(20) ;
		divisionNameChoices.getSelectionModel().select(this.teamRecentStandings.getTeam().getDivisionName());

		Button submitButton = new Button("Submit") ; 
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> addWinsEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String divisionName = divisionNameChoices.getSelectionModel().getSelectedItem() ;
				teamRecentStandings.getTeam().setDivisionName(divisionName);
				controller.saveNewDivisionForTeam(teamRecentStandings.getTeam());
				submitButton.setStyle("-fx-background-color: pink; ") ;
				teamNameLabel = setDivisionColor(teamNameLabel) ;
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, addWinsEvent);


		int x = 0 ; 
		int y = 0 ; 
		this.add(teamNameLabel, x, y);
		x++ ; 
		this.add(winsLabel, x, y);
		x++ ; 
		this.add(lossesLabel, x, y);
		x++ ; 
		this.add(divisionNameChoices, x, y);
		x++ ; 
		this.add(submitButton, x, y);
	}

	
	private ListView<String> buildDivisionNameChoices() {
		ListView<String> divisions = new ListView<String>(this.controller.buildDivisionNameList()) ;
		return divisions ;
	}


	private Label setDivisionColor(Label label) {
		label.setTextFill(Color.BLACK) ; 
		String divisionName = this.teamRecentStandings.getTeam().getDivisionName() ; 
		if("green".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.GREEN);
		}
		else if("blue".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.BLUE);
		}
		else if("red".equalsIgnoreCase(divisionName)) {
			label.setTextFill(Color.RED);
		}
		
		return label  ;
	}
}
