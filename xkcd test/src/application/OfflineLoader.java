package application;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.IntStream;

public class OfflineLoader {

	private MainWindowController mainWindowController;
	private Speicher speicher;
	private int[] savedKeys;
	private int iKey;
	
	public OfflineLoader(MainWindowController mainController) {
		mainWindowController = mainController;
		speicher = new Speicher();
		Set<Integer> keys = speicher.getKeys();
		savedKeys = new int[keys.size()];
		int i=0;
		for(int s : keys) {
			savedKeys[i] = s;
			i++;
		}
		Arrays.sort(savedKeys);
	}

	public void loadRecent() {
		iKey = savedKeys.length;
		mainWindowController.load(savedKeys[iKey]);
	}
	
	public void loadPrev() {
		mainWindowController.load(savedKeys[--iKey]);
	}
	
	public void loadNext() {
		mainWindowController.load(savedKeys[++iKey]);
	}
	
	public void loadRandom() {
		int rand = (int) (Math.random() * savedKeys.length);
		mainWindowController.load(savedKeys[rand]);
	}
	
	public void loadNumber(int number) {
		if(IntStream.of(savedKeys).anyMatch(x -> x == number)) {
			mainWindowController.load(number);
		}else {
			mainWindowController.getTextField().setText("Not available offline");
		}
	}
	
}
