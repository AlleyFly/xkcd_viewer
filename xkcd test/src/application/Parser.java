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
	 * @param number : Nummer des zu ladenden Comics
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
	 * Gibt die URL des zu ladenden Bildes als String zurück
	 * @return
	 */
	public String parseImageURL() {
		return jobject.getString("img", null);
	}
	
	
	/**
	 * Gibt die Nummer des neusten Comic zurück
	 * bei Fehlschlag 624 (persönlicher Favorit)
	 * @return
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
