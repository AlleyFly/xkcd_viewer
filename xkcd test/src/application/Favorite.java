package application;

import java.io.Serializable;
import java.util.ArrayList;

public class Favorite implements Serializable {
	private int number;
	private String title;
	
	public int getNumber() {
		return this.number;
	}
	public String getTitle() {
		return this.title;
	}
	
	public Favorite() {
		this.number = 624;
		this.title = "Branding";
	}
	
	public Favorite(int number, String title) {
		this.number = number;
		this.title = title;
	}
	
	/*public static boolean contains(ArrayList<Favorite> list, Favorite fav) {
		for(Favorite s : list) {
			if(s.number == fav.number) {
				return true;
			}
		}
		return false;
	}*/
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Favorite)
			return number == ((Favorite)obj).number;
		return false;
	}

}
