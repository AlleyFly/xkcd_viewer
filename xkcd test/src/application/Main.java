package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MainWindow.fxml"));
			AnchorPane root = loader.load();
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(URLReader.readNewest());
			Parser test = new Parser();
			Parser adware = new Parser(624);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("didnt work");
		}
		launch(args);
	}
}
