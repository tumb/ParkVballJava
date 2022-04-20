package view;

import controller.Controller;
import javafx.scene.layout.GridPane;

public class GridPaneControlled extends GridPane {
	protected Controller controller ;

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	} 
	
	

}
