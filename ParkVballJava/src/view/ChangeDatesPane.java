package view;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ChangeDatesPane extends BorderPane {

	private Controller controller ; 
	private TextField dateField ;
	private ListView<String> existingDates ; 
	
	public ChangeDatesPane(Controller controller) {
		this.controller = controller ;
		init() ;
	}

	private void init() {
		this.existingDates = new ListView<String>() ; 
		addTopPane() ; 
		addCenterPane() ; 
		addBottomPane() ; 
	}
	
	public void setExistingDates(String[] currentDates) {
		
		ObservableList<String> dateList = FXCollections.observableArrayList(currentDates) ;
		this.existingDates.getItems().clear();
		this.existingDates.setItems(dateList) ;

	}
	
	private void addCenterPane() {
		Label paneLabel = new Label("Previous Date") ; 
		Label dateListLabel = new Label("Select a date: ") ;
		GridPaneControlled centerPane = new GridPaneControlled() ; 
		
		int x = 0 ; 
		int y = 0 ; 
		centerPane.add(paneLabel, x, y);
		y++ ; 
		centerPane.add(dateListLabel, x, y);
		y++ ;
		centerPane.add(existingDates, x, y);
		
		this.setCenter(centerPane) ;
	}
	
	private void addTopPane() {
		GridPane topPane = new GridPane() ;
		topPane.add(new Label("New Date"), 1, 0);
		dateField = new TextField() ; 
		topPane.add(dateField, 1, 1);
		this.setTop(topPane) ;
		addBottomPane() ; 
	}
	
	private void addBottomPane() {
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller) ;
		bottomPane.setPadding(new Insets(10, 10, 10, 10));
		bottomPane.setVgap(20);
		bottomPane.setHgap(50);

		Button saveChangeButton = new Button("Save Change");
		saveChangeButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> saveChangeEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				saveDateChange();
			}
		};
		saveChangeButton.addEventFilter(MouseEvent.MOUSE_CLICKED, saveChangeEvent);

		bottomPane.add(saveChangeButton, 0, 0) ; 
		this.setBottom(bottomPane) ; 

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

	private void saveDateChange() {
		// TODO get the two dates as strings and pass them to the controller
		String newDate = dateField.getText() ; 
		String previousDate = existingDates.getSelectionModel().getSelectedItem() ;
		this.controller.changeScheduleDate(previousDate, newDate);
	}
	
}
