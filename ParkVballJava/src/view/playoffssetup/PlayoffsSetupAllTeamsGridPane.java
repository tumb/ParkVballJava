package view.playoffssetup;

import java.util.ArrayList;

import controller.Controller;
import controller.TeamStandings;
import javafx.geometry.Insets;
import view.GridPaneControlled;

public class PlayoffsSetupAllTeamsGridPane extends GridPaneControlled {

	public PlayoffsSetupAllTeamsGridPane(Controller controller) {
		this.controller = controller ; 
		this.setPadding(new Insets(10, 10, 10, 10));
	}
	
	
	public void setTeamStandings(ArrayList<TeamStandings> teamStandings) {
		this.getChildren().clear(); 
		for(int i = 0 ; i < teamStandings.size() ; i++) {
			SingleTeamSubpane singleTeamPane = new SingleTeamSubpane(this.controller, teamStandings.get(i)) ;
			this.add(singleTeamPane, 0, i);
		}
	}

	
}
