package application;

import java.net.*;
import java.io.*;

public class URLReader {
    
    public static String readNewest() throws Exception {
    	
    	URL newest = new URL("https://xkcd.com/info.0.json");
    	BufferedReader in = new BufferedReader(
    			new InputStreamReader(newest.openStream()));
    	
    	String inputLine, result = "";
    	while((inputLine = in.readLine()) != null)
    		result = result + inputLine;
    	in.close();
    	return result;
    }
    
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