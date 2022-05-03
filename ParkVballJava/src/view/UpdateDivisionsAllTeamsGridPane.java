package view;

import java.util.ArrayList;

import controller.Controller;
import controller.TeamRecentStandings;

public class UpdateDivisionsAllTeamsGridPane extends GridPaneControlled {

	public UpdateDivisionsAllTeamsGridPane(Controller controller) {
		this.controller = controller ; 
		
	}
	
	
	public void setTeamRecentStandings(ArrayList<TeamRecentStandings> teamRecentStandings) {
		this.getChildren().clear(); 
		for(int i = 0 ; i < teamRecentStandings.size() ; i++) {
			UpdateDivisionsSingleTeamGridPane singleTeamPane = new UpdateDivisionsSingleTeamGridPane(this.controller, teamRecentStandings.get(i)) ;
			this.add(singleTeamPane, 0, i);
		}
	}

}
