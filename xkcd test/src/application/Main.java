package application;
	

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
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

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MainWindow.fxml"));
			AnchorPane root = loader.load();
			
			
			//resize Listener
			MainWindowController controller = loader.getController();

			primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
				controller.centerImage();
			});
			primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
				controller.centerImage();
			});
			primaryStage.setOnCloseRequest(event -> {
			    controller.closeFavorites();
			});
			/*primaryStage.focusedProperty().addListener((obs, oldVal, newVal) -> {
				controller.toFrontFavorites();
			});*/ //blocks main window as long as Favorite Window is open 
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("xkcd");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
