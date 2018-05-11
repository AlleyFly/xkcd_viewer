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

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("MainWindow.fxml"));
			AnchorPane root = loader.load();
			
			
			//resize Listener
			MainWindowController controller = loader.getController();

			primaryStage.setOnCloseRequest(event -> controller.closeFavorites());
			
			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if(event.getCode() == KeyCode.NUMPAD4)
						controller.loadPrev();
					else if(event.getCode() == KeyCode.NUMPAD6)
						controller.loadNext();
					else if(event.getCode() == KeyCode.NUMPAD5)
						controller.loadRecent();
					else if(event.getCode() == KeyCode.NUMPAD0){
						controller.getTextField().requestFocus();
					}
				}
			});
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
