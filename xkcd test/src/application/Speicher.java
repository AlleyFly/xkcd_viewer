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
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import javafx.scene.image.Image;

public class Speicher {

	Path dirPath;
	Path speicherPfad;
	Map<Integer,Path> saved;
	
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
			this.writeMapFile();
		}else {
			System.out.println("Picture already saved");
		}
	}
	
	public void readMapFile() throws IOException, ClassNotFoundException {
		File file = new File(dirPath.toString()+"/saved.index");
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		saved = (HashMap<Integer,Path>) ois.readObject();
		ois.close();
	}
	
	public void writeMapFile() throws IOException {
		File file = new File(dirPath.toString()+"/saved.index");
		FileOutputStream fos= new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(saved);
		oos.close();
	}
}
