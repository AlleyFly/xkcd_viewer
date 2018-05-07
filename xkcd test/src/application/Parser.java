package application;

import com.eclipsesource.json.*;

public class Parser {
	
	private JsonValue jvalue;
	private JsonObject jobject;
	
	
	/**
	 * Standard-Konstruktor
	 * Läd den neusten Comic
	 */
	public Parser() {
		try{
			jvalue = Json.parse(URLReader.readNewest());
			jobject = jvalue.asObject();
		}catch(Exception e) {
			System.out.println("fuck");
			e.printStackTrace();
		}
	}
	
	/**
	 * Parametesierter Konstruktor
	 * Läd bestimmten Comic
	 * @param  number  Nummer des zu ladenden Comics
	 */
	public Parser(int number) {
		if(number <= getNewest()) {
			try {
				jvalue = Json.parse(URLReader.readNo(number));
				jobject = jvalue.asObject();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}else {
			System.out.println("Nummer zu groß");
		}
	}
	
	/**
	 * Parser für das Bild
	 * 
	 * @return die URL der Bilddatei als String
	 */
	public String parseImageURL() {
		return jobject.getString("img", null);
	}
	
	/**
	 * Parser für Bildtitel
	 * 
	 * @return Bildtitel als String
	 */
	public String parseTitle() {
		return jobject.getString("title", "oops");
	}
	
	
	/**
	 * Sucht Nummer des neusten Comics
	 * 
	 * @return Nummer des neusten als int, sonst 624
	 */
	public static int getNewest() {
		try {
			JsonValue jvalue = Json.parse(URLReader.readNewest());
			JsonObject jobject = jvalue.asObject();
			return jobject.getInt("num", 624);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 624;
	}
	
}
