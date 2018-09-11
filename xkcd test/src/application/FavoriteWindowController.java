package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Interface fuer das Favoritenfenster
 * @author je
 *
 */
public class FavoriteWindowController {
	
	private Control control;
	
	@FXML private TableView tabFavorites;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colTitle;
	@FXML private Button saveButton;
	@FXML private Button loadButton;
	
	/**
	 * setzt Startwerte
	 */
	@FXML
	public void initialize() {	
		colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));	
		
	}
	/**
	 * setzt Referenz zu Control
	 * @param control
	 */
	public void setControl(Control control) {
		this.control = control;
	}
	
	/**
	 * Getter für Tabelle mit Favoriten
	 * @return Favoritentabelle als TableView
	 */
	public TableView getTable() {
		return tabFavorites;
	}
	
	/**
	 * legt einen neuen Eintrag in der Tabelle an 
	 * @param fav Favorit der Eingetragen werden soll
	 */
	@FXML
	public void newEntry(XKCD fav) {
		tabFavorites.getItems().add(fav);
	}
	
	/**
	 * loescht einen Eintrag aus der Tablle
	 * @param fav Favorit der geloescht werden soll
	 */
	@FXML
	public void deleteEntry(XKCD fav) {
		tabFavorites.getItems().remove(fav);
	}
	
	/**
	 * speichert die Tabelle in externem File
	 */
	@FXML
	public void saveFavorites() {
		try {
			control.saveList();
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("couldn't save Favorites");
			e.printStackTrace();
		}
	}
	
	/**
	 * laed die Tabelle aus externem File
	 */
	@FXML
	public void loadFavorites() {
		try {
			tabFavorites.getItems().clear();
			tabFavorites.getItems().addAll(control.loadList());
		} catch (IOException | ClassNotFoundException e) {
			//e.printStackTrace();
			System.out.println("\"Favorites.sav\" konnte nicht gefunden werden");
		}
	}

}
