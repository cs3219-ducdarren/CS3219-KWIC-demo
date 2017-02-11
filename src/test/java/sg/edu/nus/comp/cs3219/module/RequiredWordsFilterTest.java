package sg.edu.nus.comp.cs3219.module;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import sg.edu.nus.comp.cs3219.model.LineStorage;

public class RequiredWordsFilterTest {
	LineStorage beforeRequiredWordsFilter;
	LineStorage afterRequiredWordsFilter;
	RequiredWordsFilter filter;
	
	@Before
	public void setUp() {
		// components
		beforeRequiredWordsFilter = new LineStorage();
		afterRequiredWordsFilter = new LineStorage();
		filter = new RequiredWordsFilter(afterRequiredWordsFilter);
		
		// required words list
		Set<String> requiredWords = new HashSet<>();
		requiredWords.add("Day");
		filter.setRequiredWords(requiredWords);
		
		// observer set up
		beforeRequiredWordsFilter.addObserver(filter);
	}

	@Test
	public void test() {
		// set up input
		beforeRequiredWordsFilter.addLine("The Day after Tomorrow");
		beforeRequiredWordsFilter.addLine("Day after Tomorrow The");
		beforeRequiredWordsFilter.addLine("after Tomorrow The Day");
		beforeRequiredWordsFilter.addLine("Tomorrow The Day after");
		
		// test results
		assertEquals(1, afterRequiredWordsFilter.size());
		assertEquals("Day after Tomorrow the", afterRequiredWordsFilter.get(0).toString());
	}
}
