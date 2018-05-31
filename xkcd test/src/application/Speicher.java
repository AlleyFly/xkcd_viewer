package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class Speicher {

	Path dirPath;
	Path speicherPfad;
	Map<Integer,Path> saved;
	
	public Speicher() {
		this.dirPath = Paths.get(Paths.get(".").toAbsolutePath().toString() + "/SavedImages");
		this.dirPath = this.dirPath.normalize();
		saved = new HashMap<Integer,Path>();
	}
	
	public void saveImage(Image image, int number, String title) throws IOException {
		if(!saved.containsKey(number)) {
			speicherPfad = Paths.get(dirPath.toString()+"/"+title+"\"");
			System.out.println(speicherPfad.toString());
			
			//save Image
			
			saved.put(number,speicherPfad);
		}else {
			System.out.println("Picture already saved");
		}
	}
}
