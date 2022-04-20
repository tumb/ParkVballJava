package view;

import java.util.ArrayList;

import controller.Controller;
import controller.Match;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class DisplaySchedulePane extends BorderPane {

	private static String RED = "-fx-text-fill: red; " ;
	private static String GREEN = "-fx-text-fill: green; " ;
	private static String BLUE = "-fx-text-fill: blue; " ;
	
	private Controller controller ;
	private LeagueChoosePane leagueChoosePane ; 
	private GridPane scheduleDisplayPane ;

	public DisplaySchedulePane(Controller controller) {
		this.controller = controller ;
		this.leagueChoosePane = new LeagueChoosePane(controller, controller.getSelectedLeague()) ;
		this.setTop(leagueChoosePane) ;
		
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

		this.scheduleDisplayPane = new GridPane() ;
		this.scheduleDisplayPane.setPadding(new Insets(5, 10, 5, 10));
		this.scheduleDisplayPane.setVgap(5);
		this.scheduleDisplayPane.setHgap(50);
		this.setCenter(scheduleDisplayPane) ;
		// TODO figure this out setBackgroundImage() ;
	}

	// This creates a cyan background with no image. :-(
	private void setBackgroundImage() {
		String filePath =  "file://C:/Pictures/Volleyball/2021/Haley Andy Thursday Green Champs 2021.jpg" ; 
		Image teamImage = new Image(filePath, 40, 40, false, true) ; 
		BackgroundImage teamBackground = new BackgroundImage(teamImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT ) ;
		this.scheduleDisplayPane.setBackground(new Background(teamBackground));
	}

	public void setSchedule(ArrayList<Match> matches) {
		this.scheduleDisplayPane.getChildren().clear() ;
		
		int y = 0  ; 
		for(int i = 0 ; i < matches.size() ; i++) {
			Match match = matches.get(i) ; 
			String matchLabel = match.toScheduleString() ; 
			Label label = new Label(matchLabel) ;
			if(match.getDivisionName().equals("red")) {
				label.setStyle(RED);
			}
			else if(match.getDivisionName().equals("green")) {
				label.setStyle(GREEN);
			}
			else if(match.getDivisionName().equals("blue")) {
				label.setStyle(BLUE);
			}
			y++ ; 
			this.scheduleDisplayPane.add(label, 0, y);
		}
		
	}

	public void setNewMatchDates(ObservableList<String> matchDates) {
		this.leagueChoosePane.setNewMatchDates(matchDates);
	}
}
