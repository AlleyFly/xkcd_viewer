package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FavoriteWindowController {
	
	MainWindowController mainController;
	
	@FXML private TableView tabFavorites;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colTitle;
	
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

}
