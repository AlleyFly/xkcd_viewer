package application;
	

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

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
	
	public static boolean isInternet() {
		try {
			URL tester = new URL("https://xkcd.com/info.0.json");
			URLConnection connection = tester.openConnection();
			connection.connect();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void start(Stage primaryStage) {

		setStage(primaryStage);
		
		try {
			Control control = new Control(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
