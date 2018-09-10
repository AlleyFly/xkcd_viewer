package application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import javax.imageio.ImageIO;

/**
 * Klasse zum Speichern und auslesen der Favoritenliste
 * @author je
 *
 */
public class Speicher {

	private Path dirPath;
	
	private Control control;
	
	/**
	 * Konstruktor erstellt Platformunabhaengig Pfad zum "SavedImages" Ordner, erstellt diesen falls nicht vorhanden
	 * @param control Refenz zu Control
	 */
	public Speicher(Control control) {
		
		this.dirPath = Paths.get(Paths.get(".").toAbsolutePath().toString() + File.separator + "SavedImages");
		this.dirPath = this.dirPath.normalize();
		new File(dirPath.toString()).mkdirs();
		this.control = control;
		//readFolder();
	}
	
	/**
	 * Prueft ob XKCD in Control.favorites vorhanden ist
	 * @param xkcd XKCD fuer welchen geprueft werden soll
	 * @return true falls enthalten, false falls nicht
	 */
	public boolean isSaved(XKCD xkcd) {
		return control.getFavorites().contains(xkcd);
	}
	
	/**
	 * Gibt Iterator fuer Control.favorites zurueck
	 * @return Control.favorites als Iterator<XKCD> 
	 */
	public Iterator<XKCD> getIterator(){
		return control.getFavorites().iterator();
	}
	
	/**
	 * Laed Pfad zu Bild
	 * @param number Nummer des XKCDs wessen Bild geladen werden soll
	 * @return Speicherpfad als Path
	 */
	public Path getPath(int number) {
		XKCD comp = new XKCD(number);
		int index = control.getFavorites().indexOf(comp);
		return control.getFavorites().get(index).getPath();
	}
	
	/**
	 * Laed Bildunterschrift
	 * @param number Nummer des zu ladenden XKCD
	 * @return Bildunterschrift als String
	 */
	public String getAlt(int number) {
		XKCD comp = new XKCD(number);
		int index = control.getFavorites().indexOf(comp);
		return control.getFavorites().get(index).getAlt();
	}
	
	/**
	 * Laed Titel
	 * @param number Nummer des zu ladenden XKCD
	 * @return Titel als String
	 */
	public String getTitle(int number) {
		XKCD comp = new XKCD(number);
		int index = control.getFavorites().indexOf(comp);
		return control.getFavorites().get(index).getTitle();
	}
	
	/**
	 * Laed Bild aus dem Netz und speichert es im "SavedImages" Ordner
	 * @param number Nummer des zu speichernden XKCD als int
	 * @param title Titel als String
	 * @return Speicherpfad als Path
	 * @throws IOException Bei einem Fehler im Parser oder bei ImageIO
	 */
	public Path saveImage(int number, String title) throws IOException {
		Parser parser = new Parser(number);
		URL imageURL = new URL(parser.parseImageURL());
		RenderedImage image = ImageIO.read(imageURL);
		File file = new File(dirPath.toString()+File.separator+number+" - "+title+".jpeg");
		ImageIO.write(image, "jpeg", file);
		
		Path speicherPfad = file.toPath();
		
		System.out.println("Bild gespeichert:\n"+number+"\t-\t"+speicherPfad);
		return speicherPfad;
	}
	
	/**
	 * Veraltete Lesefunktion
	 */
	@Deprecated
	private void readFolder() {
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
	
	private void printMap() {
		System.out.println("Gespeicherte Bilder: ");
		if(control.getFavorites().isEmpty())
			System.out.println("\tkeine Bilder gespeichert");
		for(XKCD x : control.getFavorites()) {
			System.out.println(x.getNumber() + "\t-\t" + x.getPath().toString());
		}
	}
}
