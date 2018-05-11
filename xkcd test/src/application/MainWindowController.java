package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	@FXML private Label imgLabel;
	
	private Control control;
	private FavoriteWindowController favoriteController;
	private AnchorPane root1;
	private Stage favStage;
	
	
	@FXML
	public void initialize() throws IOException {
		
		//Load fxml for FAVORITEWINDOW
		FXMLLoader favoriteTab = new FXMLLoader();
		favoriteTab.setLocation(Main.class.getResource("FavoriteWindow.fxml"));
		root1 = favoriteTab.load();
		favoriteController = favoriteTab.getController();
		favoriteController.setMainController(this);		            

		
		//set FAVORITEWINDOW parameters
		favStage = new Stage();
        favStage.setScene(new Scene(root1));  
        //set give Controller to FAVORITEWINDOW so it can call load()

		
		imageView.setPreserveRatio(true);
		this.loadRecent();
		
		control = new Control();
		
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		
	}
	
	public Control getControl() {
		return control;
	}
	
	@FXML
	public TextField getTextField() {
		return numberField;
	}
	
	public int getCurrentNumber() {
		return currentNumber;
	}
	
	public void closeFavorites() {
		if(favStage.isShowing())
			favStage.close();
	}
	
	@FXML
	public void showFavorites() {  
		if(favStage.isShowing()) {
            favStage.hide();
		} else {
	        favStage.setX(Main.getStage().getX() - 310);
	        favStage.setY(Main.getStage().getY());
			favStage.show();
		}
	}
	
	public void load(int number) {
		if(number > 0 && number <= Parser.getNewest()) {
			Parser parser = new Parser(number);
			Image image = new Image(parser.parseImageURL(),true);
			imageView.setImage(image);
			Main.getStage().setTitle(parser.parseTitle());
			imgLabel.setText(parser.parseAlt());
			currentNumber = number; //not redundant, but for calling load from favorites
			numberField.setText(Integer.toString(number));
			scrollPane.requestFocus();
		} else {
			System.out.println("Invalid Number");
			numberField.clear();
			
		}
	}
	
	@FXML
	public void loadNumber() {
		currentNumber = Integer.parseInt(numberField.getText());
		this.load(currentNumber);
	}
	
	@FXML
	public void loadRecent() {				
		currentNumber = Parser.getNewest();

		this.load(currentNumber);
		
	}
	
	@FXML
	public void loadPrev() {
		currentNumber--;
		
		this.load(currentNumber);
	}
	
	@FXML
	public void loadNext() {
		currentNumber++;
		
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
			favoriteController.deleteEntry(fav);
		}
	}
	
	@FXML
	public void handleEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			favorite();
		}
	}
	
}
