package application;

import java.util.ArrayList;
import java.util.Objects;

public class Favorite {
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

}
