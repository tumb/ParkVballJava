package view;

import java.util.ArrayList;

import controller.Controller;
import controller.League;
import controller.Player;
import controller.Team;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TeamCreationPane extends BorderPane {

	private Controller controller ; 
	private ArrayList<Team> teams ; 
	private ArrayList<Player> women ; 
	private ArrayList<Player> men ; 
	private LeagueChoosePane leagueChoosePane ; 
	private ListView<Player> menSelectionView ;
	private ListView<Player> womenSelectionView ;
	private ListView<Team> teamSelectionView ;
	private TextField teamNameField ;
	private Label menCountLabel ; 
	private Label womenCountLabel ; 
	private Label teamCountLabel ; 
	
	public TeamCreationPane(Controller controller) {
		this.controller = controller ; 
		
		this.setPadding(new Insets(10, 10, 10, 10));

		init() ; 
		addBottomPane() ;
		addLeaguePane() ; 
		addTeamEntryPane() ; 

	}
	
	private void init() {
		this.teams = new ArrayList<Team>() ; 
		this.women = new ArrayList<Player>() ; 
		this.men   = new ArrayList<Player>() ; 
		this.menSelectionView   = new ListView<Player>() ; 
		this.menSelectionView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.womenSelectionView = new ListView<Player>() ; 
		this.womenSelectionView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.teamSelectionView  = new ListView<Team>() ;
		this.teamNameField = new TextField() ;
		this.menCountLabel = new Label("Men: ?") ;
		this.womenCountLabel = new Label("Women: ?") ;
		this.teamCountLabel = new Label("Teams: ?") ; 
		
		ChangeListener<Player> womanSelected = new ChangeListener<Player>() {
			public void changed(ObservableValue<? extends Player> observable, Player old, Player newValue) {
				String defaultName = newValue.getFirstName() ;
				teamNameField.setText(defaultName) ;
			}
		};
		womenSelectionView.getSelectionModel().selectedItemProperty().addListener(womanSelected);

	}

	private void addTeamEntryPane() {
		GridPaneControlled teamEntryPane = new GridPaneControlled() ;
		teamEntryPane.setController(controller);
		teamEntryPane.setPadding(new Insets(10, 10, 10, 10));
		teamEntryPane.setVgap(10);
		teamEntryPane.setHgap(5);
		
		int x = 0 ;
		int y = 0 ; 
		Label instructionLabel = new Label("Submit 1 team at a time. Check the team name to prevent duplicates.") ; 
		teamEntryPane.add(instructionLabel, x, y, 4, 1);
		y++ ;
		
		// Labels for the lists
		x = 0 ;
		teamEntryPane.add(new Label("Men"), x, y);
		x++ ; 
		teamEntryPane.add(new Label("Women"), x, y);
		x++ ;
		x++ ; 
		teamEntryPane.add(new Label("Teams"), x, y);
		
		x = 0 ; 
		y++ ; 
		teamEntryPane.add(menSelectionView, x, y);
		x++ ; 
		teamEntryPane.add(womenSelectionView, x, y);
		x++ ;
		GridPane buttonPane = new GridPane() ;
		buttonPane.setVgap(30);
		teamEntryPane.add(buttonPane, x, y);
		addButtons(buttonPane) ;
		x++ ;
		teamEntryPane.add(teamSelectionView, x, y);
		
		// All done. Now put the pane into the mother pane
		this.setCenter(teamEntryPane);
	}

	private void addButtons(GridPane buttonPane) {
		int y = 0 ; 
		int x = 0 ; 
		
		Button addTeamButton = new Button("Add Team");
		addTeamButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> addTeamEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				addTeam();
			}
		};
		addTeamButton.addEventFilter(MouseEvent.MOUSE_CLICKED, addTeamEvent);
		buttonPane.add(addTeamButton, x, y) ;
		y++ ; 
		
		buttonPane.add(new Label("Team Name"), x, y) ;
		y++ ; 
		buttonPane.add(this.teamNameField, x, y);
		y++ ;
				
		Button submitButton = new Button("Submit Team");
		submitButton.setStyle("-fx-background-color: lightgreen; ") ;
		EventHandler<MouseEvent> submitTeamEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				submitTeam();
			}
		};
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, submitTeamEvent);
		buttonPane.add(submitButton, x, y) ;
		y++ ; 

		Button removeTeamButton = new Button("Remove Team");
		removeTeamButton.setStyle("-fx-background-color: lightblue; ") ;
		EventHandler<MouseEvent> removeTeamEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				removeTeam();
			}
		};
		removeTeamButton.addEventFilter(MouseEvent.MOUSE_CLICKED, removeTeamEvent);
		buttonPane.add(removeTeamButton, x, y) ;
		y++ ; 
		
		Button removePlayersButton = new Button("Remove Players");
		removePlayersButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> removePlayersEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				removePlayers();
			}
		};
		removePlayersButton.addEventFilter(MouseEvent.MOUSE_CLICKED, removePlayersEvent);
		buttonPane.add(removePlayersButton, x, y) ;
		y++ ; 
		
		buttonPane.add(menCountLabel, x, y);
		y++ ; 
		buttonPane.add(womenCountLabel, x, y);
		y++ ;
		buttonPane.add(teamCountLabel, x, y);
		y++ ; 

	}

	protected void removePlayers() {
		ObservableList<Player> menToRemove = this.menSelectionView.getSelectionModel().getSelectedItems() ;
		ObservableList<Player> womenToRemove = this.womenSelectionView.getSelectionModel().getSelectedItems() ;
		this.menSelectionView.getItems().removeAll(menToRemove) ;
		this.womenSelectionView.getItems().removeAll(womenToRemove) ;
		updateCounts() ; 
	}

	private void updateCounts() {
		this.menCountLabel.setText("Men: " + this.menSelectionView.getItems().size()) ; 
		this.womenCountLabel.setText("Women: " + this.womenSelectionView.getItems().size()) ;
		this.teamCountLabel.setText("Teams: " + this.teamSelectionView.getItems().size()) ;
	}

	protected void submitTeam() {
		Team team = this.teamSelectionView.getSelectionModel().getSelectedItem() ;
		controller.submitNewTeam(team) ;
	}

	protected void removeTeam() {
		// TODO Auto-generated method stub
		
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

	/**
	 * Removes those players and puts them into the Team list
	 */
	private void addTeam()  {
		Player man = this.menSelectionView.getSelectionModel().getSelectedItem() ;
		Player woman = this.womenSelectionView.getSelectionModel().getSelectedItem() ;
		League league = this.controller.getSelectedLeague() ; 
		String teamName = this.teamNameField.getText() ;
		if(league.isValid()) {
			Team team = new Team(league, man, woman, teamName) ;
			this.teamSelectionView.getItems().add(team) ;
			this.teamSelectionView.getSelectionModel().select(team); 
			// Remove the players
			this.menSelectionView.getItems().remove(man) ; 
			this.womenSelectionView.getItems().remove(woman) ;
			updateCounts() ; 
			this.teamNameField.setText(teamName) ;
		}
		else {
			controller.displayPopup("Warning!", "League not selected or invalid.") ; 
		}
		
	}

	public void setData(ArrayList<Player> men2, ArrayList<Player> women2, ArrayList<Team> teams2) {
		this.men = men2 ; 
		this.women = women2 ; 
		this.teams = teams2 ; 

		menSelectionView.getItems().clear();
		menSelectionView.getItems().setAll(this.men) ;
		this.menCountLabel.setText("Men: " + this.men.size()) ; 

		womenSelectionView.getItems().clear();
		womenSelectionView.getItems().setAll(this.women) ; 
		this.womenCountLabel.setText("Women: " + this.women.size()) ;
		
		this.teamSelectionView.getItems().clear(); 
		this.teamSelectionView.getItems().setAll(this.teams) ;
		this.teamCountLabel.setText("Teams: " + this.teams.size()) ; 
		
	}
}
