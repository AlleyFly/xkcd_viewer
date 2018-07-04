package application;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;

public class OfflineLoader {

	private MainWindowController mainWindowController;
	public Speicher speicher;
	private int[] savedKeys;
	private int iKey;
	
	public OfflineLoader(MainWindowController mainController) {
		mainWindowController = mainController;
		speicher = new Speicher();
		Set<Integer> keys = speicher.getKeys();
		savedKeys = new int[keys.size()];
		int i=0;
		for(Integer s : keys) {
			savedKeys[i] = s;
			i++;
		}
		Arrays.sort(savedKeys);
		for(int e : savedKeys) System.out.println(e);
	}

	public void loadRecent() {
		iKey = savedKeys.length-1;
		mainWindowController.loadOffline(savedKeys[iKey]);
	}
	
	public void loadPrev() {
		mainWindowController.loadOffline(savedKeys[--iKey]);
	}
	
	public void loadNext() {
		mainWindowController.loadOffline(savedKeys[++iKey]);
	}
	
	public void loadRandom() {
		int rand = (int) (Math.random() * savedKeys.length);
		mainWindowController.loadOffline(savedKeys[rand]);
	}
	
	public void loadNumber(int number) {
		if(IntStream.of(savedKeys).anyMatch(x -> x == number)) {
			mainWindowController.loadOffline(number);
		}else {
			mainWindowController.getTextField().setText("Not available offline");
		}
	}
	
}
