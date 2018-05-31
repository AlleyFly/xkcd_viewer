package application;
	

import java.nio.file.Paths;

import javafx.application.Application;
import javafx.stage.Stage;


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
