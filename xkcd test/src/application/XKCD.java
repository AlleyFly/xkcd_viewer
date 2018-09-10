package application;

import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * Eigener Variablentyp zum Speichern eines XKCD
 * @author je
 *
 */
public class XKCD implements Serializable {
	private int number;
	private String title;
	private String alt_text;
	private String path;
	
	/**
	 * @return Nummer als int
	 */
	public int getNumber() {
		return this.number;
	}
	/**
	 * @return Titel als String
	 */
	public String getTitle() {
		return this.title;
	}
	/**
	 * @return Bilduntertitel als String
	 */
	public String getAlt() {
		return alt_text;
	}
	/**
	 * @return Speicherpfad als Path
	 */
	public Path getPath() {
		return Paths.get(path);
	}
	/**
	 * Fuegt Bilduntertitel hinzu
	 * @param alt Bilduntertitel als String
	 */
	public void setAlt(String alt) {
		this.alt_text = alt;
	}
	/**
	 * Fuegt Speicherpfad hinzu
	 * @param path Speicherpfad als Path
	 */
	public void setPath(Path path) {
		this.path = path.toString();
	}
	
	/**
	 * Erstellt XKCD nur mit number (Typ 0)
	 * zum Vegleich durch .contains()
	 * @param number Keyvalue zum Vergleichen als int
	 */
	public XKCD(int number) {
		this.number = number;
	}
	/**
	 * Erstellt XKCD mit Nummer und Titel (Typ 1)
	 * als Protyp bevor das Bild gespeichert wird und die anderen Felder ausgefuellt werden koennen
	 * @param number Keyvalue als int
	 * @param title Titel als String
	 */
	public XKCD(int number, String title) {
		this.number = number;
		this.title = title;
	}
	/**
	 * Erstellt vollstaendigen XKCD (Typ 2)
	 * nicht (mehr) in verwendung, urspruenglich bein Lesen der Bilder aus readFolder verwendet
	 * @param number Keyvalue als int
	 * @param title Titel als String
	 * @param alt Untertitle als String
	 * @param path Speicherpfad als Path
	 */
	public XKCD(int number, String title, String alt, Path path) {
		this.number = number;
		this.title = title;
		this.alt_text = alt;
		this.path = path.toString();
	}
	
	/**
	 * Override fuer equals, vergleicht nur Keyvalue(Nummer)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof XKCD) {
			return this.number == ((XKCD)obj).number;
		}
		return false;
	}

}
