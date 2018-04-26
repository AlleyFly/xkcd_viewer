package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainWindowController {
	
	private int currentNumber;

	@FXML private Button recentButton;
	@FXML private Button specifiedButton;
	@FXML private Button prevButton;
	@FXML private Button nextButton;
	@FXML private TextField numberField;
	@FXML private ImageView imageView;
	
	@FXML
	public void loadNumber() {
		currentNumber = Integer.parseInt(numberField.getText());
		Parser spec = new Parser(currentNumber);
		Image image = new Image(spec.parseImageURL(),true);
		imageView.setImage(image);
		imageView.autosize();
	}
	
	@FXML
	public void loadRecent() {
		Parser recent = new Parser();
		Image image = new Image(recent.parseImageURL(),true);
		imageView.setImage(image);
		imageView.autosize();
		
		currentNumber = Parser.getNewest();
		numberField.setText(Integer.toString(currentNumber));
	}
	
	@FXML
	public void loadPrev() {
		currentNumber--;
		numberField.setText(Integer.toString(currentNumber));
		Parser spec = new Parser(currentNumber);
		Image image = new Image(spec.parseImageURL(),true);
		imageView.setImage(image);
		imageView.autosize();
	}
	
	@FXML
	public void loadNext() {
		currentNumber++;
		numberField.setText(Integer.toString(currentNumber));
		Parser spec = new Parser(currentNumber);
		Image image = new Image(spec.parseImageURL(),true);
		imageView.setImage(image);
		imageView.autosize();
	}
	
}
