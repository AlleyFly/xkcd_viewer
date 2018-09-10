package application;
	

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Mainklasse zum Start des Programms
 * @author je
 *
 */
public class Main extends Application {
	
	private static Stage pStage;
	/**
	 * Getter fuer die Stage des Hauptfensters
	 * @return Die Stage des Hauptfensters
	 */
	public static Stage getStage() {
		return pStage;
	}
	
	/**
	 * laed die Referenz zur Stage in die Variable, welche dann aufgerufen werden kann
	 * @param stage
	 */
	public void setStage(Stage stage) {
		pStage = stage;
	}
	/**
	 * Prueft ob eine Verbindung mit der API hergestellt werden kann
	 * @return true wenn Verbindung hergestellt werden konnte, false falls nicht
	 */
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
			@SuppressWarnings("unused")
			Control control = new Control(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
