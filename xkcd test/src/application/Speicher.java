package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;

public class Speicher {

	Path dirPath;
	Path speicherPfad;
	HashMap<Integer,Path> saved;
	
	public Speicher() {
		
		this.dirPath = Paths.get(Paths.get(".").toAbsolutePath().toString() + File.separator + "SavedImages");
		this.dirPath = this.dirPath.normalize();
		new File(dirPath.toString()).mkdirs();
		saved = new HashMap<Integer,Path>();
		readFolder();
	}
	
	public boolean isSaved(int number) {
		return saved.containsKey(number);
	}
	
	public Path getPath(int number) {
		return saved.get(number);
	}
	
	public void saveImage(int number, String title) throws IOException {
		if(!saved.containsKey(number)) {
			
			//save Image
			Parser parser = new Parser(number);
			URL imageURL = new URL(parser.parseImageURL());
			RenderedImage image = ImageIO.read(imageURL);
			File file = new File(dirPath.toString()+File.separator+number+" - "+title+".jpeg");
			ImageIO.write(image, "jpeg", file);
			
			speicherPfad = file.toPath();
			saved.put(number,speicherPfad);
			System.out.println("Bild gespeichert:\n"+number+"\t-\t"+speicherPfad);
		}else {
			System.out.println("Bild bereits gespeichert");
			printMap();
		}
	}
	
	public void readFolder() {
		saved.clear();
		File folder = new File(dirPath.toString());
		File[] fileList = folder.listFiles();
		for(File file : fileList) {
			if(file.isFile()) {
				String toWork = file.getName();
				String[] array = toWork.split(" - ");			
				saved.put(Integer.parseInt(array[0]), Paths.get(file.getAbsolutePath()));
			}
		}
		printMap();
	}
	
	private void printMap() {
		Iterator it = saved.entrySet().iterator();
		System.out.println("Gespeicherte Bilder: ");
		while(it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			System.out.println(pair.getKey()+"\t-\t"+pair.getValue());
		}
	}
}
