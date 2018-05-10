package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Control {
	ArrayList<Favorite> favorites;
	
	public Control() {
		favorites = new ArrayList<Favorite>();
	}
	
	public boolean addFavorite(Favorite fav) {
		if(favorites.contains(fav)) {
			favorites.remove(fav);
			return false;
		}else {
			favorites.add(fav);
			return true;
		}
	}
	
	public Favorite[] getAllFavorites() {
		return favorites.toArray(new Favorite[favorites.size()]);
	}
	
	public void saveList() throws IOException {
		FileOutputStream fos = new FileOutputStream("Favorites.sav");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(favorites);
		oos.close();
	}
	
	public ArrayList<Favorite> loadList() throws ClassNotFoundException, IOException {
		FileInputStream fis = new FileInputStream("Favorites.sav");
		ObjectInputStream ois = new ObjectInputStream(fis);
		favorites = (ArrayList<Favorite>) ois.readObject();
		ois.close();
		return favorites;
	}

}
