package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.crypto.spec.OAEPParameterSpec;
import javax.imageio.ImageIO;

public class Speicher {

	Path dirPath;
	Path speicherPfad;
	HashMap<Integer,Path> saved;
	
	Control control;
	
	public Speicher(Control control) {
		this.dirPath = Paths.get(Paths.get(".").toAbsolutePath().toString() + "/SavedImages");
		this.dirPath = this.dirPath.normalize();
		saved = new HashMap<Integer,Path>();
		this.control = control;
		try {
			this.readMapFile();
		} catch (ClassNotFoundException | IOException e) {
			System.out.println("Oops");
			e.printStackTrace();
		}
	}
	
	public void saveImage(int number, String title) throws IOException {
		if(!saved.containsKey(number)) {
			
			//save Image
			Parser parser = new Parser(number);
			URL imageURL = new URL(parser.parseImageURL());
			RenderedImage image = ImageIO.read(imageURL);
			File file = new File(dirPath.toString()+"/"+title);
			ImageIO.write(image, "jpeg", file);
			
			speicherPfad = file.toPath();
			System.out.println(speicherPfad.toString());
			saved.put(number,speicherPfad);
			writeSingleEntry(new AbstractMap.SimpleEntry<Integer, Path>(number, speicherPfad));
		}else {
			System.out.println("Picture already saved");
			printMap();
		}
	}
	
	public void readMapFile() throws IOException, ClassNotFoundException {
		File index = new File("index.sav");
		FileInputStream fis = new FileInputStream(index);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<Integer,String> toRead = new HashMap<Integer,String>();
		toRead = (HashMap<Integer,String>) ois.readObject();
		ois.close();
		Iterator it = toRead.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			saved.put((Integer)pair.getKey(), Paths.get((String)pair.getValue()));
		}
	}
	
	public void writeCompleteMapFile() throws IOException {
		Map toSave = new HashMap();
		File index = new File("index.sav");
		FileOutputStream fos = new FileOutputStream(index);
		ObjectOutputStream oos = new ObjectOutputStream(fos);	
		Iterator it = saved.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			toSave.put(pair.getKey(), pair.getValue().toString());
		}
		oos.writeObject(toSave);
		oos.close();
	}
	
	public void writeSingleEntry(Map.Entry<Integer,Path> e) throws IOException {
		File index = new File("index.sav");
		FileOutputStream fos = new FileOutputStream(index);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		Map toSave = new HashMap();
		toSave.put(e.getKey(),e.getValue().toString());
		oos.writeObject(toSave);
		oos.close();
	}
	
	private void printMap() {
		Iterator it = saved.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println("Key: "+pair.getKey()+"\tValue: "+pair.getValue());
		}
	}
}
