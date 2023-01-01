package view.playoffssetup;

import controller.Controller;
import controller.TeamStandings;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SingleTeamSubpane extends GridPane {

	private Controller controller ;
	private TeamStandings teamStandings ; 
	
	private Label teamNameLabel ;
	private Label seasonPointsLabel ; 
	// Set up radio buttons. This is not easily expandable for a 4th choice but I don't see a good way around that. 
	private RadioButton redDivisionChoice ; 
	private RadioButton greenDivisionChoice ; 
	private RadioButton blueDivisionChoice ; 
	private ToggleGroup divisionChoicesGroup ; 
	private HBox divisionButtonsBox ; 
	private Button teamHistoryButton ; 
	
	private TextField seedTextField ; 
	
	public SingleTeamSubpane(Controller controller, TeamStandings teamStandings) {
		this.controller = controller ; 
		this.teamStandings = teamStandings ; 
		
		this.teamNameLabel = new Label(teamStandings.getTeamName()) ; 
		this.seasonPointsLabel = new Label("" + teamStandings.getPoints()) ;
		this.seedTextField = new TextField() ; 
		
		buildTeamHistoryButton() ; 
		buildDivisionRadioButtons() ;
		setCurrentDivision() ; 

		this.setHgap(20);
		ColumnConstraints teamNameConstraint = new ColumnConstraints(80) ; 
		ColumnConstraints pointsConstraint = new ColumnConstraints(30) ;
		ColumnConstraints buttonConstraint = new ColumnConstraints(80) ;
		ColumnConstraints divisionsConstraint = new ColumnConstraints(180) ; 

		this.getColumnConstraints().addAll(teamNameConstraint, pointsConstraint, buttonConstraint, divisionsConstraint, pointsConstraint) ; 
		
		int x = 0 ; 
		int y = 0 ; 
		this.add(this.teamNameLabel, x, y);
		x++ ; 
		this.add(this.seasonPointsLabel, x, y);
		x++ ; 
		this.add(this.teamHistoryButton, x, y);
		x++ ; 
		this.add(this.divisionButtonsBox, x, y);
		x++ ; 
		this.add(this.seedTextField, x, y);
	}
	
	private void setCurrentDivision() {
		String currentDivision = this.teamStandings.getCurrentDivision() ;
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
	
	// TODO this will need to be made to work in controller. Want to show team record for tie breaking.
	private void buildTeamHistoryButton() {
		this.teamHistoryButton = new Button("Games") ; 
		teamHistoryButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> submitNewDivisionEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Toggle selection = divisionChoicesGroup.getSelectedToggle() ;
				String divisionName = selection.getUserData().toString() ;
				teamStandings.setCurrentDivision(divisionName);
				//controller.saveNewDivisionForTeam(team);
				teamHistoryButton.setStyle("-fx-background-color: pink; ") ;
				teamNameLabel = setDivisionColor(teamNameLabel) ;
			}
		};
		teamHistoryButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitNewDivisionEvent);
	}
	
	private Label setDivisionColor(Label label) {
		label.setTextFill(Color.BLACK) ; 
		String divisionName = this.teamStandings.getCurrentDivision() ; 
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
