package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MainWindowController {
	
	private int currentNumber;

	@FXML private Button recentButton;
	@FXML private Button specifiedButton;
	@FXML private Button prevButton;
	@FXML private Button nextButton;
	@FXML private TextField numberField;
	@FXML private ImageView imageView;
	@FXML private ScrollPane scrollPane;
	@FXML private StackPane stackPane;
	
	@FXML
	public void initialize() {
		imageView.setPreserveRatio(true);
		this.loadRecent();
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
	
}
