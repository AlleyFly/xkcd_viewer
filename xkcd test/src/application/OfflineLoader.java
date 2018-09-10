package application;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Uebernimmt laden der XKCD's wenn kein Internet vorhanden ist.
 * Hat ein Feld der gespeicherten XKCD, durch welches Itariert wird
 * @author je
 *
 */
public class OfflineLoader {

	private MainWindowController mainWindowController;
	/**
	 * Refenz zum Speicher
	 */
	public Speicher speicher;
	private ArrayList<Integer> savedKeys;
	private int iKey;
	
	/**
	 * Konstruktor
	 * @param mainController Refenz zum Maincontroller
	 * @param speicher Refenz zum Speicher
	 */
	public OfflineLoader(MainWindowController mainController, Speicher speicher) {
		mainWindowController = mainController;
		this.speicher = speicher;
		Iterator<XKCD> keys = speicher.getIterator();
		savedKeys = new ArrayList<Integer>();
		keys.forEachRemaining(e -> savedKeys.add(e.getNumber()));
		System.out.println("saved Keys");
		for(int e : savedKeys) System.out.println(e);
		iKey = savedKeys.size();
	}

	/**
	 * Laed den neusten Eintrag in den Favoriten
	 * @return Nummer des Eintrags als int
	 */
	public int loadRecent() {
		iKey = savedKeys.size()-1;
		int currentNumber = savedKeys.get(iKey);
		mainWindowController.loadOffline(currentNumber);
		return currentNumber;
	}
	/**
	 * Laed den vorherigen Eintrag in den Favoriten
	 * @return Nummer des Eintrags als int
	 */
	public int loadPrev() {
		try {
			int currentNumber = savedKeys.get(--iKey);
			mainWindowController.loadOffline(currentNumber);
			return currentNumber;
		}catch(IndexOutOfBoundsException e) {
			mainWindowController.loadInvalid("Erster Favorit erreicht.");
			iKey = -1;
			return savedKeys.get(0);
		}
	}
	/**
	 * Laed den naechsten Eintrag in den Favoriten
	 * @return Nummer des Eintrags als int
	 */
	public int loadNext() {
		try {
			int currentNumber = savedKeys.get(++iKey);
			mainWindowController.loadOffline(currentNumber);
			return currentNumber;
		}catch(IndexOutOfBoundsException e) {
			mainWindowController.loadInvalid("Letzter Favorit erreicht.");
			iKey = savedKeys.size();
			return savedKeys.get(savedKeys.size()-1);
		}
	}
	/**
	 * Laed einen zufaelligen Eintrag aus den Favoriten
	 * @return Nummer des Eintrags als int
	 */
	public int loadRandom() {
		int rand = (int) (Math.random() * savedKeys.size());
		int currentNumber = savedKeys.get(rand);
		mainWindowController.loadOffline(currentNumber);
		return currentNumber;
	}
	
	/**
	 * Laed die angegebene Nummer falls vorhanden, sonst das Fehlerbild
	 * @param number zu ladende Nummer als int
	 */
	public void loadNumber(int number) {
		if(savedKeys.contains(number)) {
			mainWindowController.loadOffline(number);
		}else {
			mainWindowController.loadInvalid("Offline nicht verfügbar.");
			mainWindowController.getScrollPane().requestFocus();
		}
	}
	
}
