package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FavoriteWindowController {
	
	private Control control;
	
	@FXML private TableView tabFavorites;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colTitle;
	@FXML private Button saveButton;
	@FXML private Button loadButton;
	
	@FXML
	public void initialize() {
		
		colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));	
		
	}
	
	public void setControl(Control control) {
		this.control = control;
	}
	
	public TableView getTable() {
		return tabFavorites;
	}
	
	@FXML
	public void newEntry(XKCD fav) {
		tabFavorites.getItems().add(fav);
	}
	
	@FXML
	public void deleteEntry(XKCD fav) {
		tabFavorites.getItems().remove(fav);
	}
	
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
