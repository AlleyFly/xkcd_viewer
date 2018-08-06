package application;

import java.io.File;
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

public class Control {
	private ArrayList<XKCD> favorites;
	
	public  MainWindowController mainController;
	public FavoriteWindowController favController;
	public OfflineLoader offLoader;
	public Speicher speicher;

	private Stage primaryStage;
	private Stage favoriteStage;
	
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
		
		this.primaryStage = primaryStage;
		this.favoriteStage = favStage;
		
		primaryStage.setScene(new Scene(root));		
		
		setListeners();
		
		if(Main.isInternet())
			mainController.loadRecent();
		else
			offLoader.loadRecent();
		
		primaryStage.show();
		openFavorites();
	}
	
	public MainWindowController getMainController() {
		return mainController;
	}
	
	public FavoriteWindowController getFavController() {
		return favController;
	}
	
	/**
	 * toggle the Favorite Window
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
	 * Favoriten-Tab oeffnen
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
	 * closing the Favorite Window
	 * mainly used to close it when Main Window is closed
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
	 * sets all the Listeners
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
	 * adding a Favorite to the list(favorites)
	 * 
	 * @param fav Favorite to add
	 * @return true if its a new Favorite, false if it is already a Favorite
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
	 * Getter für Favoriten
	 * 
	 * @return Array of Favorites
	 */
	public ArrayList<XKCD> getFavorites() {
		return favorites;
	}
	
	
	/**
	 * Speichert Favoriten als File "Favorites.sav"
	 * 
	 * @throws IOException
	 */
	public void saveList() throws IOException {
		FileOutputStream fos = new FileOutputStream("Favorites.sav");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(favorites);
		oos.close();
	}
	
	/**
	 * Läd Favoriten als Liste, aus File "Favorites.sav"
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
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
	 * Für Debug Zwecke
	 */
	public void printFavorites() {
		for(XKCD x : favorites) {
			System.out.println(x.getNumber()+" - "+x.getTitle()+" - "+x.getPath());
		}
	}
}
