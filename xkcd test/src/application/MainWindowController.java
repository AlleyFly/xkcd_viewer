package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainWindowController {
	
	private int currentNumber;

	@FXML private Button recentButton;
	@FXML private Button specifiedButton;
	@FXML private Button prevButton;
	@FXML private Button nextButton;
	@FXML private Button favButton;
	@FXML private TextField numberField;
	@FXML private ImageView imageView;
	@FXML private ScrollPane scrollPane;
	@FXML private StackPane stackPane;
	@FXML private Button favWindowButton;
	
	Control control;
	FavoriteWindowController favoriteController;
	AnchorPane root1;
	
	@FXML
	public void initialize() throws IOException {
		imageView.setPreserveRatio(true);
		control = new Control();
		this.loadRecent();
		
		FXMLLoader favoriteTab = new FXMLLoader();
		favoriteTab.setLocation(Main.class.getResource("FavoriteWindow.fxml"));
		root1 = favoriteTab.load();
		
		favoriteController = favoriteTab.getController();
	}
	
	@FXML
	public void showFavorites() {  
		favoriteController.setMainController(this);
	        try {
	            Stage stage = new Stage();
	            stage.setScene(new Scene(root1));  
	            stage.show();
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@FXML
	public void centerImage() {
		stackPane.setPrefSize(scrollPane.getWidth(), scrollPane.getHeight());
	}
	
	public void load(int number) {
		Parser parser = new Parser(number);
		Image image = new Image(parser.parseImageURL(),true);
		imageView.setImage(image);
	}
	
	@FXML
	public void loadNumber() {
		currentNumber = Integer.parseInt(numberField.getText());
		
		this.load(currentNumber);	
	}
	
	@FXML
	public void loadRecent() {				
		currentNumber = Parser.getNewest();
		numberField.setText(Integer.toString(currentNumber));

		this.load(currentNumber);
		
	}
	
	@FXML
	public void loadPrev() {
		currentNumber--;
		numberField.setText(Integer.toString(currentNumber));
		
		this.load(currentNumber);
	}
	
	@FXML
	public void loadNext() {
		currentNumber++;
		numberField.setText(Integer.toString(currentNumber));
		
		this.load(currentNumber);
	}
	
	@FXML
	public void favorite() {
		//get title
		Parser parser = new Parser(currentNumber);
		String title = parser.parseTitle();
		
		Favorite fav = new Favorite(currentNumber, title);
		if(control.addFavorite(fav)) {
			favoriteController.newEntry(fav);
		}else {
			System.out.println("already favd");
		}
	}
	
}
