package view;

import java.util.ArrayList;

import controller.Controller;
import controller.TeamStandings;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class StandingsPane extends BorderPane {
	Controller controller ; 
	TableView<TeamStandings> table ; 
	LeagueChoosePane leagueChoosePane ; 
	
	public StandingsPane(Controller controller ) {
		this.controller = controller ; 
		this.leagueChoosePane = new LeagueChoosePane(controller, controller.getSelectedLeague()) ;
		this.setTop(leagueChoosePane) ;
		table = new TableView<TeamStandings>() ; 
		this.setCenter(table);
		
		TableColumn<TeamStandings, String> rankColumn = new TableColumn<>("Rank") ; 
		rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank")); 
		table.getColumns().add(rankColumn) ; 
		TableColumn<TeamStandings, String> manColumn = new TableColumn<>("Man") ; 
		manColumn.setCellValueFactory(new PropertyValueFactory<>("manName")); 
		table.getColumns().add(manColumn) ; 
		TableColumn<TeamStandings, String> womanColumn = new TableColumn<>("Woman") ; 
		womanColumn.setCellValueFactory(new PropertyValueFactory<>("womanName")); 
		table.getColumns().add(womanColumn) ; 
		TableColumn<TeamStandings, String> pointsColumn = new TableColumn<>("Points") ; 
		pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points")); 
		table.getColumns().add(pointsColumn) ; 
		TableColumn<TeamStandings, String> divisionColumn = new TableColumn<>("Division") ; 
		divisionColumn.setCellValueFactory(new PropertyValueFactory<>("currentDivision")); 
		table.getColumns().add(divisionColumn) ; 
		
		GridPaneControlled bottomPane = new GridPaneControlled() ;
		bottomPane.setController(this.controller);
		
		Button exitButton = new Button("Exit") ; 
		exitButton.setStyle("-fx-background-color: pink; ") ;
		EventHandler<MouseEvent> cancelEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				controller.returnToHomePane();
			}
		};
		exitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelEvent);

		
		this.setBottom(bottomPane);
		bottomPane.add(exitButton, 0, 0);
		
		
		
				
	}

	public void setStandings(ArrayList<TeamStandings> teamStandings) {
		table.getItems().clear() ;
		for(int i = 0 ; i < teamStandings.size() ; i++) {
			table.getItems().add(teamStandings.get(i)) ;
		}
			
	}
}
