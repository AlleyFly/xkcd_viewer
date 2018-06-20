package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class IndexWriter {

	File file;
	HashMap<Integer,Path> saved;
	
	public IndexWriter(HashMap<Integer,Path> saved) throws FileNotFoundException {
		file = new File("saved.index");
		this.saved = saved;
		
	}
	
	private void readFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		reader.close();
	}
	
	public void writeAll() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		Set<Integer> savedKeys = saved.keySet();
		Iterator<Integer> iterator = savedKeys.iterator();
		for(int i=0;i<savedKeys.size();i++) {
			Integer key = iterator.next();
			writer.write(key.toString()+" "+saved.get(key).toString()+"\n");
		}
		writer.close();
	}
}
