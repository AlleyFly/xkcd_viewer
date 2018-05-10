package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FavoriteWindowController {
	
	MainWindowController mainController;
	
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
	
	public void setMainController(MainWindowController mwController) {
		this.mainController = mwController;
		
		tabFavorites.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Favorite fav = (Favorite) tabFavorites.getSelectionModel().getSelectedItem();
		    	mainController.load(fav.getNumber());
		    }
		});
	}
	
	@FXML
	public void newEntry(Favorite fav) {
		tabFavorites.getItems().add(fav);
	}
	
	@FXML
	public void deleteEntry(Favorite fav) {
		tabFavorites.getItems().remove(fav);
	}
	
	@FXML
	public void saveFavorites() {
		try {
			mainController.getControl().saveList();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("couldn't save Favorites");
		}
	}
	
	@FXML
	public void loadFavorites() {
		try {
			tabFavorites.getItems().clear();
			tabFavorites.getItems().addAll(mainController.getControl().loadList());
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("couldn't load Favorites");
		}
	}

}
