package view;

import java.util.ArrayList;

import controller.Controller;
import controller.Match;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class UpdateResultsMatchGrid extends GridPaneControlled {

	public UpdateResultsMatchGrid(Controller controller) {
		this.controller = controller;
		this.setPadding(new Insets(0, 0, 0, 20)); // The left inset is the last one?!
		this.setVgap(0);
		this.setHgap(5);
	}

	public void setAllMatches(ArrayList<Match> matches) {
		this.getChildren().clear() ;
		for(int i = 0 ; i < matches.size() ; i++) {
			UpdateResultsSingleMatchPane resultsOfMatch = new UpdateResultsSingleMatchPane(controller, matches.get(i)) ;
			this.add(resultsOfMatch, 0, i) ;
		}
	}

	public void removeSingleResultPane(Match match) {
		int soughtId = match.getId() ;
		int foundIndex = -1 ; 
		ObservableList<Node> allDisplayedMatches = this.getChildren() ;
		for(int i = 0 ; i < allDisplayedMatches.size() ; i++) {
			UpdateResultsSingleMatchPane matchPane = (UpdateResultsSingleMatchPane)allDisplayedMatches.get(i) ;
			int id = matchPane.getMatchId() ;
			if(id == soughtId) {
				foundIndex = i ; 
			}
		}
		if(foundIndex >= 0) {
			this.getChildren().remove(foundIndex) ;
		}
	}

	public ArrayList<Match> getAllDisplayedMatches() {
		ArrayList<Match> allDisplayedMatches = new ArrayList<Match>() ;
		ObservableList<Node> displays = this.getChildren() ;
		for(int i = 0 ; i < displays.size() ; i++) {
			UpdateResultsSingleMatchPane matchPane = (UpdateResultsSingleMatchPane)displays.get(i) ;
			allDisplayedMatches.add(matchPane.getMatch()) ; 
		}
		return allDisplayedMatches;
	}
	
}
