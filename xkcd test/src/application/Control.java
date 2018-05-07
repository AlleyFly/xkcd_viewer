package application;

import java.util.ArrayList;

public class Control {
	ArrayList<Favorite> favorites;
	
	public Control() {
		favorites = new ArrayList<Favorite>();
	}
	
	public boolean addFavorite(Favorite fav) {
		if(favorites.contains(fav)) { //returns false, always: boils down to Object.java --> return (this == fav) --> different hash
			System.out.println("Is already Favorited");
			return false;
		}else {
			favorites.add(fav);
			return true;
		}
	}
	
	public Favorite[] getAllFavorites() {
		return favorites.toArray(new Favorite[favorites.size()]);
	}

}
