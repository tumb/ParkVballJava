package view;

import controller.Controller;
import controller.TeamRecentStandings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class UpdateDivisionsSingleTeamGridPane extends GridPane{
	
	private Controller controller ; 
	private TeamRecentStandings teamRecentStandings ; 
	private Label teamNameLabel ;
	// Set up radio buttons. This is not easily expandable for a 4th choice but I don't see a good way around that. 
	private RadioButton redDivisionChoice ; 
	private RadioButton greenDivisionChoice ; 
	private RadioButton blueDivisionChoice ; 
	private ToggleGroup divisionChoicesGroup ; 
	private HBox divisionButtonsBox ; 
	
	public UpdateDivisionsSingleTeamGridPane(Controller controller, TeamRecentStandings teamRecentStandings) {
		this.controller = controller ; 
		this.teamRecentStandings = teamRecentStandings ; 
		
		this.setHgap(20);
		ColumnConstraints teamNameConstraint = new ColumnConstraints(80) ; 
		ColumnConstraints digitConstraint = new ColumnConstraints(10) ;
		ColumnConstraints divisionsConstraint = new ColumnConstraints(180) ; 

		this.getColumnConstraints().addAll(teamNameConstraint, digitConstraint, digitConstraint, divisionsConstraint) ; 
		
		Label winsLabel = new Label("" + this.teamRecentStandings.getWins()) ;
		Label lossesLabel = new Label("" + this.teamRecentStandings.getLosses()) ;
		teamNameLabel = new Label(this.teamRecentStandings.getTeam().getTeamName()) ; 
		teamNameLabel = setDivisionColor(teamNameLabel) ;
		
		buildDivisionRadioButtons() ;
		setCurrentDivision() ; 
		
		Button submitButton = new Button("Submit") ; 
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitNewDivisionEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Toggle selection = divisionChoicesGroup.getSelectedToggle() ;
				String divisionName = selection.getUserData().toString() ;
				teamRecentStandings.getTeam().setDivisionName(divisionName);
				controller.saveNewDivisionForTeam(teamRecentStandings.getTeam());
				submitButton.setStyle("-fx-background-color: pink; ") ;
				teamNameLabel = setDivisionColor(teamNameLabel) ;
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitNewDivisionEvent);


		int x = 0 ; 
		int y = 0 ; 
		this.add(teamNameLabel, x, y);
		x++ ; 
		this.add(winsLabel, x, y);
		x++ ; 
		this.add(lossesLabel, x, y);
		x++ ; 
		this.add(divisionButtonsBox, x, y);
		x++ ; 
		this.add(submitButton, x, y);
	}

	
	private void setCurrentDivision() {
		String currentDivision = this.teamRecentStandings.getTeam().getDivisionName() ;
		if("red".equals(currentDivision)) {
			this.redDivisionChoice.setSelected(true);
		}
		else if("green".equals(currentDivision)) {
			this.greenDivisionChoice.setSelected(true);
		}
		else if("blue".equals(currentDivision)) {
			this.blueDivisionChoice.setSelected(true);
		}
	}

	private void buildDivisionRadioButtons() {
		this.divisionChoicesGroup = new ToggleGroup() ; 
		this.divisionButtonsBox = new HBox() ; 
		this.divisionButtonsBox.setSpacing(20);
		ObservableList<String> divisionList = this.controller.buildDivisionNameList() ;
		for(int i = 0 ; i < divisionList.size() ; i++) {
			String divisionName = divisionList.get(i) ;
			if("red".equals(divisionName)) {
				this.redDivisionChoice = new RadioButton("red") ;
				this.redDivisionChoice.setUserData(divisionName);
				this.redDivisionChoice.setToggleGroup(divisionChoicesGroup);
				this.divisionButtonsBox.getChildren().add(this.redDivisionChoice) ;
				this.redDivisionChoice.setTextFill(Color.RED) ; 
			}
			if("green".equals(divisionName)) {
				this.greenDivisionChoice = new RadioButton("green") ;
				this.greenDivisionChoice.setUserData(divisionName);
				this.greenDivisionChoice.setToggleGroup(divisionChoicesGroup);
				this.divisionButtonsBox.getChildren().add(this.greenDivisionChoice) ;
				this.greenDivisionChoice.setTextFill(Color.GREEN) ; 
			}
			if("blue".equals(divisionName)) {
				this.blueDivisionChoice = new RadioButton("blue") ;
				this.blueDivisionChoice.setUserData(divisionName);
				this.blueDivisionChoice.setToggleGroup(divisionChoicesGroup);
				this.divisionButtonsBox.getChildren().add(this.blueDivisionChoice) ;
				this.blueDivisionChoice.setTextFill(Color.BLUE) ; 
			}
		}
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
