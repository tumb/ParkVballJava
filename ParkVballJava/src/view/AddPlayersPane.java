package view;

import controller.Controller;
import controller.Player;
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
import javafx.scene.layout.GridPane;

public class AddPlayersPane extends BorderPane {
	
	private Controller controller ; 
	private ListView<String> playerList ; 
	private TextField firstNameTextField ; 
	private TextField lastNameTextField ; 
	private TextField phoneTextField ; 
	private TextField emailTextField ; 
	private Button submitButton ; 
	private RadioButton maleButton ; 
	private RadioButton femaleButton ;
	private ToggleGroup genderToggleGroup ; 
	
	public AddPlayersPane(Controller controller) {
		this.controller = controller ; 
		
		this.setPadding(new Insets(10, 10, 10, 10));

		initSubmitButton() ;
		addBottomPane() ;
		addPlayerListPane() ; 
		addPlayerEntryPane() ; 
	}

	private void initSubmitButton() {
		this.submitButton = new Button("Submit") ; 
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Player player = readPlayer() ; 
				controller.submitSubmitNewPlayer(player);
			}

			private Player readPlayer() {
				String firstName = firstNameTextField.getText() ;
				String lastName = lastNameTextField.getText() ;
				String phone = phoneTextField.getText() ;
				String email = emailTextField.getText() ;
				String gender = "F" ; 
				if(maleButton.isSelected()) {
					gender = "M" ;
				}
				Player player = new Player(firstName, lastName, gender, email, phone, 0) ;
				return player;
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitEvent) ;
		
	}

	private void addPlayerEntryPane() {
		GridPane addPlayerPane = new GridPane() ; 
		addPlayerPane.setPadding(new Insets(10, 10, 10, 10));
		addPlayerPane.setVgap(10);
		addPlayerPane.setHgap(10);

		
		int x = 0 ;
		int y = 0 ; 
		addPlayerPane.add(new Label("New Player"), x, y);
		
		y++ ;
		addPlayerPane.add(new Label("First name"), x, y);
		x++ ;
		addPlayerPane.add(new Label("Last name"), x, y);
		x++; 
		addPlayerPane.add(new Label("Gender"), x, y);
		x = 0 ;
		y++ ; 
		firstNameTextField = new TextField() ; 
		addPlayerPane.add(firstNameTextField, x, y);
		x++ ; 
		lastNameTextField = new TextField() ; 
		addPlayerPane.add(lastNameTextField, x, y);
		x++ ; 
		this.genderToggleGroup = new ToggleGroup() ; 
		this.maleButton = new RadioButton("Male") ;
		this.femaleButton = new RadioButton("Female") ;
		maleButton.setToggleGroup(genderToggleGroup);
		femaleButton.setToggleGroup(genderToggleGroup);
		addPlayerPane.add(maleButton, x, y);
		y++ ; 
		addPlayerPane.add(femaleButton, x, y);

		x= 0 ;
		y++; 
		addPlayerPane.add(new Label("Email Address"), x, y);
		x++ ;
		addPlayerPane.add(new Label("Phone"), x, y);
		x = 0 ;
		y++ ; 
		emailTextField = new TextField() ; 
		addPlayerPane.add(emailTextField, x, y);
		x++ ; 
		phoneTextField = new TextField() ; 
		addPlayerPane.add(phoneTextField, x, y);
		x++ ; 
		addPlayerPane.add(submitButton, x, y);
		
		this.setTop(addPlayerPane);
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

	public void addToAllPlayersList(Player player) {
		this.playerList.getItems().add(player.toString()) ;
	}
	

}
