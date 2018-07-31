package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;

public class Speicher {

	Path dirPath;
	Path speicherPfad;
	
	Control control;
	
	public Speicher(Control control) {
		
		this.dirPath = Paths.get(Paths.get(".").toAbsolutePath().toString() + File.separator + "SavedImages");
		this.dirPath = this.dirPath.normalize();
		new File(dirPath.toString()).mkdirs();
		this.control = control;
		//readFolder();
	}
	
	public boolean isSaved(XKCD xkcd) {
		return control.getFavorites().contains(xkcd);
	}
	
	public Iterator<XKCD> getIterator(){
		return control.getFavorites().iterator();
	}
	
	public Path getPath(int number) {
		XKCD comp = new XKCD(number);
		int index = control.getFavorites().indexOf(comp);
		return control.getFavorites().get(index).getPath();
	}
	
	public Path saveImage(int number, String title) throws IOException {
		Parser parser = new Parser(number);
		URL imageURL = new URL(parser.parseImageURL());
		RenderedImage image = ImageIO.read(imageURL);
		File file = new File(dirPath.toString()+File.separator+number+" - "+title+".jpeg");
		ImageIO.write(image, "jpeg", file);
		
		speicherPfad = file.toPath();
		
		System.out.println("Bild gespeichert:\n"+number+"\t-\t"+speicherPfad);
		return speicherPfad;
	}
	
	/**
	 * Liest Bilder aus altem Speichersystem ein
	 * (Favoriten und gespeicherte Bilder waren getrennt)
	 */
	@Deprecated
	public void readFolder() {
		control.getFavorites().clear();
		File folder = new File(dirPath.toString());
		File[] fileList = folder.listFiles();
		for(File file : fileList) {
			if(file.isFile()) {
				String toWork = file.getName();
				String[] array = toWork.split(" - ");	
				Parser parser = new Parser(Integer.parseInt(array[0]));
				control.getFavorites().add(new XKCD(Integer.parseInt(array[0]),parser.parseTitle(),parser.parseAlt(), Paths.get(file.getAbsolutePath())));
			}
		}
		printMap();
	}
	
	public void printMap() {
		System.out.println("Gespeicherte Bilder: ");
		if(control.getFavorites().isEmpty())
			System.out.println("\tkeine Bilder gespeichert");
		for(XKCD x : control.getFavorites()) {
			System.out.println(x.getNumber() + "\t-\t" + x.getPath().toString());
		}
	}
}
