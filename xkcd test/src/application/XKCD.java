package application;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XKCD implements Serializable {
	private int number;
	private String title;
	private String alt_text;
	private String path;
	
	public int getNumber() {
		return this.number;
	}
	public String getTitle() {
		return this.title;
	}
	public String getAlt() {
		return alt_text;
	}
	public Path getPath() {
		return Paths.get(path);
	}
	public void setAlt(String alt) {
		this.alt_text = alt;
	}
	public void setPath(Path path) {
		this.path = path.toString();
	}
	
	/**
	 * Erstellt XKCD nur mit number,
	 * zum Vegleich mit .contains()
	 * @param number Key-Wert zum Vergleichen
	 */
	public XKCD(int number) {
		this.number = number;
	}
	
	public XKCD(int number, String title) {
		this.number = number;
		this.title = title;
	}
	
	public XKCD(int number, String title, String alt, Path path) {
		this.number = number;
		this.title = title;
		this.alt_text = alt;
		this.path = path.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof XKCD) {
			return this.number == ((XKCD)obj).number;
		}
		return false;
	}

}
