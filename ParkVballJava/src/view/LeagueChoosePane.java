package view;

import controller.Controller;
import controller.League;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

/**
 * This will be the top menu bar on a few panes - displays and sets: the day of week, division, year, and date 
 * @author thom
 *
 */
public class LeagueChoosePane extends GridPaneControlled {
		League league ; 
		ListView<String> divisionList ;
		ListView <String> dateList ;
		ListView<String> yearList ;
		ListView<String> dayOfWeekList ;
		ChangeListener<String> divisionChange ;
		
	public LeagueChoosePane(Controller controller, League defaultLeague) {
		this.controller = controller;
		this.league = defaultLeague ; 
		
		this.setPadding(new Insets(10, 10, 10, 10));
		this.setVgap(20);
		this.setHgap(5);
		this.setStyle("-fx-background-color: lightblue") ;
	
		yearList = new ListView<String>();
		yearList.setItems(controller.buildYearList());
		ChangeListener<String> yearChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				controller.setLeagueYear(newValue);
			}
		};
		yearList.getSelectionModel().selectedItemProperty().addListener(yearChange);

		yearList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		yearList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		dayOfWeekList = new ListView<String>();
		dayOfWeekList.setItems(controller.buildDayList());
		ChangeListener<String> dayChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				enableListeners(false) ;
				controller.setLeagueDay(newValue);
				enableListeners(true) ;
			}
		};
		dayOfWeekList.getSelectionModel().selectedItemProperty().addListener(dayChange) ;
		dayOfWeekList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		dayOfWeekList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);


		this.divisionList = new ListView<String>();
		divisionList.setItems(controller.buildDivisionNameList());
		this.divisionChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				enableListeners(false) ;
				controller.setLeagueDivisionName(newValue);
				enableListeners(true) ;
			}
		};
		divisionList.getSelectionModel().selectedItemProperty().addListener(divisionChange);
		divisionList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		divisionList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		this.dateList = new ListView<String>() ; 
		dateList.setItems(controller.buildDateList(league)) ;
		ChangeListener<String> dateChange = new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String old, String newValue) {
				ObservableList<String> selections = dateList.getSelectionModel().getSelectedItems() ;
				if(selections.size() == 1) {
					controller.setMatchDate(newValue);
				}
				else if(selections.size() > 1) {
					controller.setMultipleMatchDates(selections) ;
				}
			}
		};
		dateList.getSelectionModel().selectedItemProperty().addListener(dateChange); 
		dateList.setMaxHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);
		dateList.setMinHeight(ApplicationFX.MAX_HEIGHT_OF_SHORT_LIST);

		this.add(new Label("Day"), 0, 0) ;
		this.add(new Label("Date"), 1, 0) ;
		this.add(new Label("Division"), 2, 0) ;
		this.add(new Label("Year"), 3, 0) ;

		this.add(dayOfWeekList, 0, 1) ;
		this.add(dateList, 1, 1) ;
		this.add(divisionList, 2, 1) ;
		this.add(yearList, 3, 1) ;
		
	}
	
	public void setDefaults() {
		this.yearList.getSelectionModel().select("" + this.league.getYear());
		this.dayOfWeekList.getSelectionModel().select(this.league.getDayOfWeek());
		this.divisionList.getSelectionModel().select("blue") ;
	}

	public void setNewMatchDates(ObservableList<String> newDates) {
		this.dateList.getItems().clear();
		this.dateList.setItems(newDates) ;
	}

	public void removeDivisionSection() {
		this.getChildren().remove(divisionList) ;
	}

	public void makeDatesMultiselect() {
		this.dateList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		// put in a multiselection listener in place of the single section listener
		
	}

	public void setLeague(League league) {
		if(league.isValid()) {
			this.league = league ; 
			this.yearList.getSelectionModel().select("" + this.league.getYear());
			this.dayOfWeekList.getSelectionModel().select(this.league.getDayOfWeek());
		}
	}
	
	private void enableListeners(boolean enable) {
		if(enable) {
			this.divisionList.getSelectionModel().selectedItemProperty().addListener(divisionChange);
		}
		else {
			this.divisionList.getSelectionModel().selectedItemProperty().removeListener(divisionChange);
		}

	}
}
