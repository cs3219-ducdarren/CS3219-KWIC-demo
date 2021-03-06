package sg.edu.nus.comp.cs3219.module;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import sg.edu.nus.comp.cs3219.event.LineStorageChangeEvent;
import sg.edu.nus.comp.cs3219.model.Line;
import sg.edu.nus.comp.cs3219.model.LineStorage;

public class RequiredWordsFilter implements Observer {
	final private LineStorage resultStorage;
	private Set<String> requiredWords = new HashSet<>();

	private boolean isEmpty;
	
	public RequiredWordsFilter(LineStorage storage) {
		resultStorage = storage;
	}
	
	public void setRequiredWords(Set<String> requiredWordSet) {
		requiredWords = requiredWordSet;
		isEmpty = true;
		for(String s : requiredWords) {
			if (!s.equals("")) {
				isEmpty = false;
				break;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		LineStorage storage = (LineStorage) o;
		LineStorageChangeEvent event = (LineStorageChangeEvent) arg;
		switch (event.getEventType()) {
		case ADD:
			Line line = storage.get(event.getChangedLine());
			if (isRequiredWordFirst(line)) {
				addToResult(line);
			} else {
				// do nothing
			}
			break;
		default:
			break;
		}
	}
	
	private boolean isRequiredWordFirst(Line line) {
		// empty required list means anything is permitted
		String key = line.getWord(0);
		System.out.println(requiredWords);
		return isEmpty || requiredWords.contains(key) || requiredWords.contains(key.toLowerCase());
	}
	
	private void addToResult(Line line) {
		resultStorage.addLine(line);
	}

}
