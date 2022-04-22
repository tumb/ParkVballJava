package view;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class AddPlayersPane extends BorderPane {
	
	private Controller controller ; 
	private ListView<String> playerList ; 
	
	public AddPlayersPane(Controller controller) {
		this.controller = controller ; 
		
		addBottomPane() ;
		addPlayerListPane() ; 
		// addPlayerEntryPane() ; 
		
	}
	public void addPlayerListPane() {
		GridPane playerListPane = new GridPane() ; 
		this.playerList = new ListView<String>() ;
		playerListPane.add(new Label("Existing Players (to avoid duplicates)"), 0, 0);
		playerListPane.add(playerList, 0, 1);
		this.setCenter(playerListPane);
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
	public void setPlayers(ObservableList<String> existingPlayers) {
		this.playerList.getItems().clear(); 
		this.playerList.setItems(existingPlayers);
	}
	

}
