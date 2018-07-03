package application;

import java.io.Serializable;

public class Favorite implements Serializable {
	private int number;
	private String title;
	
	public int getNumber() {
		return this.number;
	}
	public String getTitle() {
		return this.title;
	}
	
	public Favorite(int number, String title) {
		this.number = number;
		this.title = title;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Favorite)
			return number == ((Favorite)obj).number;
		return false;
	}

}
