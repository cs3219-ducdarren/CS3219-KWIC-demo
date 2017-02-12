package sg.edu.nus.comp.cs3219.control;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sg.edu.nus.comp.cs3219.model.LineStorage;
import sg.edu.nus.comp.cs3219.module.Alphabetizer;
import sg.edu.nus.comp.cs3219.module.CircularShifter;
import sg.edu.nus.comp.cs3219.module.RequiredWordsFilter;

public class MasterControl {
	private final Alphabetizer alphabetizer;
	private final CircularShifter shifter;
	private final RequiredWordsFilter rwf;

	private LineStorage rawInputLines;
	private LineStorage resultLines;
	private LineStorage intermediateLines;

	public MasterControl() {
		// Storage
		rawInputLines = new LineStorage();
		intermediateLines = new LineStorage();
		resultLines = new LineStorage();

		// Sub-modules
		shifter = new CircularShifter(intermediateLines);
		rwf = new RequiredWordsFilter(resultLines);
		alphabetizer = new Alphabetizer();

		// Set up observation
		rawInputLines.addObserver(shifter);
		intermediateLines.addObserver(rwf);
		resultLines.addObserver(alphabetizer);

	}
	
	public List<String> run(List<String> input, Set<String> ignoredWords, Set<String> requiredWords) {
		rawInputLines.clearLines();
		intermediateLines.clearLines();
		resultLines.clearLines();

		// Set ignore words (make them lowercase for comparison)
		shifter.setIgnoreWords(this.transformSetToLowercase(ignoredWords));
		rwf.setRequiredWords(this.transformSetToLowercase(requiredWords));
		
		// Add data line by line
		for (String line : input) {
			rawInputLines.addLine(line);
		}
		
		return resultLines.getAll();
	}

	public List<String> run(List<String> input, Set<String> ignoredWords) {
	    return run(input, ignoredWords, new HashSet<String>());
	}
	
	private Set<String> transformSetToLowercase(Set<String> set) {
		return set.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
	}
}
