package application;
	

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	private static Stage pStage;
	
	public static Stage getStage() {
		return pStage;
	}
	
	public void setStage(Stage stage) {
		pStage = stage;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			
			setStage(primaryStage);

			Control control = new Control(primaryStage);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
