package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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
			writeSingleEntry();
		}else {
			System.out.println("Picture already saved");
		}
	}
	
	public void readMapFile() throws IOException, ClassNotFoundException {
		
	}
	
	public void writeCompleteMapFile() throws IOException {
		IndexWriter iWriter = new IndexWriter(saved);
		iWriter.writeAll();
	}
	
	public void writeSingleEntry(Map.Entry<Integer,Path> e) {
		
	}
}
