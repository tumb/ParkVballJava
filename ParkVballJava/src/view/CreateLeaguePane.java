package view;

import controller.Controller;
import controller.League;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class CreateLeaguePane extends BorderPane {

	private Controller controller ; 
	private TextField yearTextField ; 
	private ListView<String> dayList ; 
	private RadioButton threeDivisionsButton ; 
	private RadioButton twoDivisionsButton ;
	private ToggleGroup divisionCountToggleGroup ; 
	private League league ; 
	private Button submitButton ; 
	private Button cancelButton ; 
	
	public CreateLeaguePane(Controller controller) { 		
		this.controller = controller;

		initSubmitButton() ;
		buildAndAddBottomPane() ; 
		buildAndAddCenterPane() ; 
	}
	
	private void initSubmitButton() {
		this.submitButton = new Button("Submit") ; 
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				League league = readLeague() ; 
				controller.submitNewLeague(league);
			}

			private League readLeague() {
				String yearString = yearTextField.getText() ;
				int divisionCount = 2 ; 
				if(threeDivisionsButton.isSelected()) {
					divisionCount = 3 ;
				}
				String dayOfWeek = dayList.getSelectionModel().getSelectedItem() ; 
				League league = new League(yearString, dayOfWeek, divisionCount) ; 
				return league ;
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEvent) ;
	}

	public void buildAndAddCenterPane() {
		GridPaneControlled centerPane = new GridPaneControlled() ;
		centerPane.setController(this.controller) ;
		centerPane.setPadding(new Insets(10, 10, 10, 10));
		centerPane.setVgap(20);
		centerPane.setHgap(50);
		
		int x = 0 ;
		int y = 0 ; 
		centerPane.add(new Label("New League"), x, y);
		
		y++ ;
		centerPane.add(new Label("Year"), x, y);
		x++ ;
		centerPane.add(new Label("Day of Week"), x, y);
		x++; 
		centerPane.add(new Label("Number of Divisions"), x, y);
		
		x = 0 ;
		y++ ; 		
		
		this.yearTextField = new TextField() ;
		centerPane.add(this.yearTextField, x, y) ;
		x++ ;
		
		this.dayList = buildListOfDays() ; 
		centerPane.add(this.dayList, x, y) ;
		x++ ; 

		this.divisionCountToggleGroup = new ToggleGroup() ; 
		this.twoDivisionsButton = new RadioButton("Two") ;
		this.threeDivisionsButton = new RadioButton("Three") ;
		twoDivisionsButton.setToggleGroup(divisionCountToggleGroup);
		threeDivisionsButton.setToggleGroup(divisionCountToggleGroup);
		centerPane.add(twoDivisionsButton, x, y);
		y++ ; 
		centerPane.add(threeDivisionsButton, x, y);
		
		this.setCenter(centerPane) ;
	}
	
	private ListView<String> buildListOfDays() {
		ListView<String> listView = new ListView<String>() ;
		ObservableList<String> listOfDays = FXCollections.observableArrayList("Testday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday") ;
		listView.setItems(listOfDays);
		listView.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST) ;

		return listView ; 
	}
	
	public void buildAndAddBottomPane() {
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setVgap(20);
		bottomPane.setHgap(50);

		bottomPane.add(this.submitButton, 0, 0) ;
		
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
