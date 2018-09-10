package application;

import java.net.*;
import java.io.*;
/**
 * Ruft die API auf und gibt den enthaltenen String zurueck
 * @author je
 *
 */
public class URLReader {
    
	/**
	 * Ruft die API fuer den Neusten XKCD auf
	 * @return Inalt der API als String
	 * @throws IOException wenn Verbindug fehlgeschlagen ist
	 */
    public static String readNewest() throws IOException {
    	URL newest = new URL("https://xkcd.com/info.0.json");
    	BufferedReader in = new BufferedReader(
    			new InputStreamReader(newest.openStream()));
    	
    	String inputLine, result = "";
    	while((inputLine = in.readLine()) != null)
    		result = result + inputLine;
    	in.close();
    	return result;
    }
    /**
     * Ruft die API fuer eine bestimmte Nummer auf
     * @param number zu ladende Nummer als int
     * @return Inhlt der API als String
     * @throws Exception wenn keine Verbindung hergestellt werden konnte
     */
    public static String readNo(int number) throws Exception {
    	URL url = new URL("https://xkcd.com/"+number+"/info.0.json");
    	BufferedReader in = new BufferedReader(
    			new InputStreamReader(url.openStream()));
    	
    	String inputLine, result = "";
    	while((inputLine = in.readLine()) != null)
    		result = result + inputLine;
    	in.close();
    	return result;
    }
}