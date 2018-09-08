package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Diese Klasse stellt das Interface für das Hauptfenster dar,
 * zudem ist sie für das laden der Bilder zuständig.
 * @author je
 *
 */
public class MainWindowController {
	
	private Control control;
	private OfflineLoader offLoader;
	
	private int currentNumber;
	private boolean isInternet;
	
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
	@FXML private CheckBox offlineCheckbox;
	
	/**
	 * Setzt Startwerte für das MainWindow
	 */
	@FXML
	public void initialize() {	
		isInternet = Main.isInternet();
		
		//Schaltet auf Offlinemodus um, falls keine Verbindung mit dem Internet hergestellt werden kann.
		if(!isInternet) {
			offlineCheckbox.fire();
			offlineCheckbox.disableProperty().set(true);
		}
		
		//zentriert Bild im ScrollPane
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
	}
	
	public void setControl(Control control) {
		this.control = control;
	}
	
	public void setOfflineLoader(OfflineLoader offLoader) {
		this.offLoader = offLoader;
	}

	public TextField getTextField() {
		return numberField;
	}
	
	public ScrollPane getScrollPane() {
		return scrollPane;
	}
	
	public int getCurrentNumber() {
		return currentNumber;
	}
	
	/**
	 * Der Ladealgorithmus für gespeicherte XKCDs
	 * @param number Die Nummer des zu ladenden XKCD
	 */
	public void loadOffline(int number) {
		try {
			currentNumber = number;
			File file = new File(offLoader.speicher.getPath(number).toUri());
			FileInputStream in = new FileInputStream(file);
			Image image = new Image(in);
			imageView.setImage(image);
			numberField.setText(Integer.toString(number));
			imgLabel.setText(offLoader.speicher.getAlt(number));
			Main.getStage().setTitle(offLoader.speicher.getTitle(number));
			scrollPane.requestFocus();
		}catch(Exception e) {
			System.out.println("Load failed");
			loadInvalid(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Hauptladealgorithmus
	 * @param number Nummer des XKCDs welcher geladen werden soll.
	 */
	public void load(int number) {
		if(number > 0 && number <= Parser.getNewest()) {
			Image image;
			Parser parser = new Parser(number);
			if(offLoader.speicher.isSaved(new XKCD(number))) {
				try {
					File file = new File(offLoader.speicher.getPath(number).toUri());
					FileInputStream in = new FileInputStream(file);
					image = new Image(in);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("File imput for Image failed.\nloading from Web...");
					image = new Image(parser.parseImageURL(),true);
				}
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
			//numberField.setPromptText("Invalid Number");
			loadInvalid("Invalid Number");
			
		}
	}
	
	@FXML
	public void loadNumber() {
		currentNumber = Integer.parseInt(numberField.getText());

		if(isInternet)
			this.load(currentNumber);
		else
			offLoader.loadNumber(currentNumber);
	}
	
	@FXML
	public void loadRecent() {	
		if(isInternet) {
			currentNumber = Parser.getNewest();
	
			this.load(currentNumber);
		}else
			offLoader.loadRecent();
	}
	
	@FXML
	public void loadPrev() {
		if(isInternet) {
			currentNumber--;
			
			this.load(currentNumber);
		}else
			offLoader.loadPrev();
	}
	
	@FXML
	public void loadNext() {
		if(isInternet) {
			currentNumber++;
			
			this.load(currentNumber);
		}else
			offLoader.loadNext();
	}
	
	@FXML
	public void loadRandom() {
		if(isInternet) {
			int max = Parser.getNewest();
			int random = (int)(Math.random() * max +1);	
			currentNumber = random;
			
			this.load(currentNumber);
		}else
			offLoader.loadRandom();
	}
	
	/**
	 * Funktion welche im Fehlerfall aufgerufen wird.
	 * @param message Fehlernachicht welche auf dem Textstreifen eingeblended wird.
	 */
	public void loadInvalid(String message) {
			Image image;
			File file = new File("."+File.separator+"1969 - Not Available.jpeg");
			try {
				image = new Image(new FileInputStream(file));
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				System.out.println("Error:Image not Found");
			}
			Main.getStage().setTitle("Error");
			imgLabel.setText(message);
			numberField.setText(Integer.toString(currentNumber));
			scrollPane.requestFocus();
	}
	
	@FXML
	public void showFavorites() {
		control.showFavorites();
	}
	
	/**
	 * Fügt XKCD der Favoritenliste hinzu, speichert Bild, Titel und Subtitel.
	 * Falls das XKCD ein Duplikat ist werden die Einträge entfernt, aber die Bilddatei an sich bleibt bestehen.
	 */
	@FXML
	public void favorite() {
		if(isInternet) {
			try {
				Parser parser = new Parser(currentNumber);
				String title = parser.parseTitle();
	
				XKCD fav = new XKCD(currentNumber,title);
				
				if(control.addFavorite(fav)) {
					Path path = offLoader.speicher.saveImage(currentNumber, title);
					fav.setAlt(parser.parseAlt());
					fav.setPath(path);
					control.getFavController().newEntry(fav);
				}else {
					control.getFavController().deleteEntry(fav);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			XKCD fav = new XKCD(currentNumber);
			if(control.addFavorite(fav)) {
				System.out.println("Neue Favoriten können im Offline Modus nicht hinzugefügt werden...");
				control.addFavorite(fav);
			}else {
				control.getFavController().deleteEntry(fav);
			}
		}
	}
	
	/**
	 * Schaltet den Offlinemodus ein bzw. aus.
	 */
	@FXML
	public void switchNetworkMode() {
		if(offlineCheckbox.isSelected())
			isInternet = false;
		else
			isInternet = true;
	}
	
	@FXML
	public void debug() {
		System.out.println("Network-Mode: "+isInternet);
		control.printFavorites();
		System.out.println("Control favorites");
		control.speicher.printMap();
		System.out.println("offLoader favorites");
		offLoader.speicher.printMap();
	}
	
}
