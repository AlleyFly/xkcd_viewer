package application;

import java.util.ArrayList;

public class Control {
	ArrayList<Integer> favorites;
	
	public Control() {
		favorites = new ArrayList<Integer>();
	}
	
	public void addFavorite(int number) {
		if(favorites.contains(number)) {
			System.out.println("Is already Favorited");
		}else {
			favorites.add(number);
		}
	}
	
	public Integer[] getAllFavorites() {
		return favorites.toArray(new Integer[favorites.size()]);
	}

}
