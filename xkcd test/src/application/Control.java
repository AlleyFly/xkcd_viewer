package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Hauptklasse der Anwendung
 * @author je
 *
 */
public class Control {
	/**
	 * Liste der Favorisierten XKCD's
	 */
	private ArrayList<XKCD> favorites;	
	
	/**
	 * Referenz zum Aufrufen
	 */
	public MainWindowController mainController;
	/**
	 * Referenz zum Aufrufen
	 */
	public FavoriteWindowController favController;
	/**
	 * Referenz zum Aufrufen
	 */
	public OfflineLoader offLoader;
	/**
	 * Referenz zum Aufrufen
	 */
	public Speicher speicher;

	private Stage primaryStage;
	private Stage favoriteStage;
	
	/**
	 * Konstruktor fuer Control, laed FXML, die Favoritenliste, erstellt Speicher sowie Offlineloader, prueft Internetverbindung und Oeffnet die Fenster
	 * @param primaryStage Die in Main angelegte Stage wird uebergeben
	 * @throws IOException Bei einem Fehler des FXML-loaders
	 */
	public Control(Stage primaryStage) throws IOException {
		
		favorites = new ArrayList<XKCD>();	
		
		try {
			loadList();
		} catch (java.io.FileNotFoundException e) {
			saveList();
		} catch (ClassNotFoundException e) {
			System.out.println("Corrupted Savefile for Favorites (ClassNotFoundException)");
		}
		
		//load MainWindow FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MainWindow.fxml"));
		AnchorPane root = loader.load();
		
		
		mainController = loader.getController();
		
		speicher = new Speicher(this);
		offLoader = new OfflineLoader(mainController, speicher);
		
		mainController.setOfflineLoader(offLoader);
		mainController.setControl(this);
		
		
		
		
		
		//load FavWindow FXML
		FXMLLoader favoriteTab = new FXMLLoader();
		favoriteTab.setLocation(Main.class.getResource("FavoriteWindow.fxml"));
		AnchorPane root1 = favoriteTab.load();
		
		favController = favoriteTab.getController();
		favController.setControl(this);
		
		//set FAVORITEWINDOW parameters
		Stage favStage = new Stage();
        favStage.setScene(new Scene(root1));  
		
        //set Stages
		this.primaryStage = primaryStage;
		this.favoriteStage = favStage;
		
		primaryStage.setScene(new Scene(root));		
		
		setListeners();
		
		//auf Internet pruefen und neusten XKCD laden
		if(Main.isInternet())
			mainController.loadRecent();
		else
			offLoader.loadRecent();
		
		primaryStage.show();
		openFavorites();
	}
	
	/**
	 * Getter fuer Controller des Hauptfensters
	 * @return Referenz zum Controller
	 */
	public MainWindowController getMainController() {
		return mainController;
	}
	
	/**
	 * Getter fuer Controller des Favoriten-Tabs
	 * @return Referenz zum Controller
	 */
	public FavoriteWindowController getFavController() {
		return favController;
	}
	
	/**
	 * Schaltet das Favoriten-Tab auf oder zu
	 */
	public void showFavorites() {  
		if(favoriteStage.isShowing()) {
            favoriteStage.hide();
		} else {
	        favoriteStage.setX(Main.getStage().getX() - 310);
	        favoriteStage.setY(Main.getStage().getY());
			favoriteStage.show();
			favController.loadFavorites();
		}
	}
	
	/**
	 * oeffnet Favoriten-Tab
	 */
	public void openFavorites() {
		if(!favoriteStage.isShowing()) {
			favoriteStage.setX(Main.getStage().getX() - 310);
	        favoriteStage.setY(Main.getStage().getY());
			favoriteStage.show();
			favController.loadFavorites();
			primaryStage.requestFocus();
		}
	}
	
	/**
	 * Schließt das Favoriten-Tab
	 */
	public void closeFavorites() {
		if(favoriteStage.isShowing())
			try {
				saveList();
			} catch (IOException e) {
				System.out.println("Failed to save Favorites to File");
			}
			favoriteStage.close();
	}
	
	/**
	 * Setzt Listener fuer NUMPAD-Navigation, laden aus Favoriten sowie schließen des Favoriten-Tabs bei beenden der Anwendung
	 */
	public void setListeners() {
		
		//Click Favorites in Table to load them
		TableView tabFavorites = favController.getTable();
		tabFavorites.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != oldSelection) {
		    	XKCD fav = (XKCD) tabFavorites.getSelectionModel().getSelectedItem();
		    	mainController.loadOffline(fav.getNumber());
		    }
		});
		
		//Close Favorite Window when Main Window closes
		primaryStage.setOnCloseRequest(event -> closeFavorites());

		//Numpad Listener for Navigation
		primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.NUMPAD4)
					mainController.loadPrev();
				else if(event.getCode() == KeyCode.NUMPAD6)
					mainController.loadNext();
				else if(event.getCode() == KeyCode.NUMPAD5)
					mainController.loadRandom();
				else if(event.getCode() == KeyCode.NUMPAD0)
					mainController.getTextField().requestFocus();
				else if(event.getCode() == KeyCode.NUMPAD8)
					mainController.loadRecent();
				else if(event.getCode() == KeyCode.ENTER)
					mainController.favorite();
			}
		});
	}
	
	
	
	/**
	 * Fuegt Favorit der Liste hinzu
	 * 
	 * @param fav Favorit welcher hinzugefuegt werden soll
	 * @return true bei erfolgreichem hinzufuegen, false bei Duplikat in Liste
	 */
	public boolean addFavorite(XKCD fav) {
		if(favorites.contains(fav)) {
			favorites.remove(fav);
			return false;
		}else {
			favorites.add(fav);
			return true;
		}
	}
	
	
	/**
	 * Getter fuer Favoriten
	 * 
	 * @return Favoriten als ArrayList<XKCD>
	 */
	public ArrayList<XKCD> getFavorites() {
		return favorites;
	}
	
	
	/**
	 * Speichert Favoriten als File "Favorites.sav"
	 * 
	 * @throws IOException falls Savefile nicht gefunden wurde
	 */
	public void saveList() throws IOException {
		FileOutputStream fos = new FileOutputStream("Favorites.sav");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(favorites);
		oos.close();
	}
	

	/**
	 * Laed Liste aus Savefile "Favorites.sav"
	 * @return Favoriten als ArrayList<XKCD>
	 * @throws ClassNotFoundException falls Savefile korrupt ist
	 * @throws IOException falls Savefile nicht gefunden wurde
	 */
	public ArrayList<XKCD> loadList() throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("Favorites.sav");
		ObjectInputStream ois = new ObjectInputStream(fis);
		favorites = (ArrayList<XKCD>) ois.readObject();
		ois.close();
		return favorites;
	}
	
	/**
	 * Gibt alle Favoriten aus
	 * Fuer Debug Zwecke
	 */
	private void printFavorites() {
		for(XKCD x : favorites) {
			System.out.println(x.getNumber()+" - "+x.getTitle()+" - "+x.getPath());
		}
	}
}
