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

	public int loadRecent() {
		iKey = savedKeys.size()-1;
		int currentNumber = savedKeys.get(iKey);
		mainWindowController.loadOffline(currentNumber);
		return currentNumber;
	}
	
	public int loadPrev() {
		try {
			int currentNumber = savedKeys.get(--iKey);
			mainWindowController.loadOffline(currentNumber);
			return currentNumber;
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Reached first favorite");
			iKey++;
			return savedKeys.get(0);
		}
	}
	
	public int loadNext() {
		try {
			int currentNumber = savedKeys.get(++iKey);
			mainWindowController.loadOffline(currentNumber);
			return currentNumber;
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Reached last favorite");
			iKey--;
			return savedKeys.get(savedKeys.size()-1);
		}
	}
	
	public int loadRandom() {
		int rand = (int) (Math.random() * savedKeys.size());
		int currentNumber = savedKeys.get(rand);
		mainWindowController.loadOffline(currentNumber);
		return currentNumber;
	}
	
	public void loadNumber(int number) {
		if(savedKeys.contains(number)) {
			mainWindowController.loadOffline(number);
		}else {
			mainWindowController.getTextField().setText("Not available offline");
			mainWindowController.getScrollPane().requestFocus();
		}
	}
	
}
