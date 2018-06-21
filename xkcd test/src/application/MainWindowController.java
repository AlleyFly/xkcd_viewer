package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;


public class MainWindowController {
	
	private Control control;
	
	private int currentNumber;

	@FXML private Button recentButton;
	@FXML private Button randomButton;
	@FXML private Button specifiedButton;
	@FXML private Button prevButton;
	@FXML private Button nextButton;
	@FXML private Button favButton;
	@FXML private TextField numberField;
	@FXML private ImageView imageView;
	@FXML private ScrollPane scrollPane;
	@FXML private StackPane stackPane;
	@FXML private Button favWindowButton;
	@FXML private Button saveButton;
	@FXML private Label imgLabel;
	
	
	@FXML
	public void initialize() throws IOException {				
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
	}
	
	public void setControl(Control control) {
		this.control = control;
	}
	
	@FXML
	public TextField getTextField() {
		return numberField;
	}
	
	public int getCurrentNumber() {
		return currentNumber;
	}
	
	public void load(int number) {
		if(number > 0 && number <= Parser.getNewest()) {
			Image image;
			Parser parser = new Parser(number);
			if(control.speicher.isSaved(number)) {
				System.out.println(control.speicher.getPath(number).toUri().toString());
				image = new Image(control.speicher.getPath(number).toUri().toString(),true);
			}else {
				image = new Image(parser.parseImageURL(),true);
			}
			imageView.setImage(image);
			Main.getStage().setTitle(parser.parseTitle());
			imgLabel.setText(parser.parseAlt());
			currentNumber = number;
			numberField.setText(Integer.toString(number));
			scrollPane.requestFocus();
		} else {
			numberField.clear();
			numberField.setPromptText("Invalid Number");
			
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
	public void loadRandom() {
		int max = Parser.getNewest();
		int random = (int)(Math.random() * max +1);	
		currentNumber = random;
		
		this.load(currentNumber);
	}
	
	@FXML
	public void save() {
		try {
			control.speicher.saveImage(currentNumber, Main.getStage().getTitle());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void showFavorites() {
		control.showFavorites();
	}
	
	@FXML
	public void favorite() {
		//get title
		Parser parser = new Parser(currentNumber);
		String title = parser.parseTitle();
		
		Favorite fav = new Favorite(currentNumber, title);
		if(control.addFavorite(fav)) {
			control.getFavController().newEntry(fav);
		}else {
			control.getFavController().deleteEntry(fav);
		}
	}
	
	@FXML
	public void handleEnter(KeyEvent event) {
		if(event.getCode() == KeyCode.ENTER) {
			favorite();
		}
	}
	
}
