package application;

import java.util.ArrayList;
import java.util.Iterator;

public class OfflineLoader {

	private MainWindowController mainWindowController;
	public Speicher speicher;
	private ArrayList<Integer> savedKeys;
	private int iKey;
	
	
	//Problemfaktor
	public OfflineLoader(MainWindowController mainController, Speicher speicher) {
		mainWindowController = mainController;
		//speicher = new Speicher();
		this.speicher = speicher; //evtl fehler durch lok. kopie statt pointer?
		Iterator<XKCD> keys = speicher.getIterator();
		savedKeys = new ArrayList<Integer>();
		keys.forEachRemaining(e -> savedKeys.add(e.getNumber()));
		System.out.println("saved Keys");
		for(int e : savedKeys) System.out.println(e);
		iKey = savedKeys.size();
	}

	public void loadRecent() {
		iKey = savedKeys.size();
		mainWindowController.loadOffline(savedKeys.get(iKey));
	}
	
	public void loadPrev() {
		mainWindowController.loadOffline(savedKeys.get(--iKey));
	}
	
	public void loadNext() {
		mainWindowController.loadOffline(savedKeys.get(++iKey));
	}
	
	public void loadRandom() {
		int rand = (int) (Math.random() * savedKeys.size());
		mainWindowController.loadOffline(savedKeys.get(rand));
	}
	
	public void loadNumber(int number) {
		if(savedKeys.contains(number)) {
			mainWindowController.loadOffline(number);
		}else {
			mainWindowController.getTextField().setText("Not available offline");
		}
	}
	
}
