package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FavoriteWindowController {
	
	@FXML private TableView tabFavorites;
	@FXML private TableColumn colNumber;
	@FXML private TableColumn colTitle;
	
	@FXML
	public void initialize() {
		
		colNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
		colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		
		tabFavorites.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Favorite fav = (Favorite) tabFavorites.getSelectionModel().getSelectedItem();
		    	//load(fav.getNumber());
		    }
		});
	}

}
