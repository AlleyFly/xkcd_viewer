package application;

import com.eclipsesource.json.*;

public class Parser {
	
	private JsonValue jvalue;
	private JsonObject jobject;
	private int newestNum;
	
	
	/**
	 * Standard-Konstruktor
	 * Läd den neusten Comic
	 */
	public Parser() {
		try{
			jvalue = Json.parse(URLReader.readNewest());
			jobject = jvalue.asObject();
			newestNum = jobject.getInt("num", 0);
			//System.out.println(jobject.getString("title", "defaultValue"));
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
		try {
			jvalue = Json.parse(URLReader.readNo(number));
			jobject = jvalue.asObject();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String parseImageURL() {
		return jobject.getString("img", null);
	}
	
}
