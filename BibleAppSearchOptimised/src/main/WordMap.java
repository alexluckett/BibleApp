package main;

import java.util.*;

/**
 * Maps a list of words to a set of appearances. Used for quick searching.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
 */
public class WordMap {
	private Map<String, List<Appearance>> words = new HashMap<String, List<Appearance>>(); // hashmap is O(1) retrieval time
	
	public void addWord(String word, String book, int verse, int chapter) {
		List<Appearance> appearances = getAppearances(word); // search is not case sensitive
		
		if(appearances == null) { // no appearances have been logged
			/* Don't initialise with an initial value!this decreases performance since
			 *  the actual word count could range anywhere between 0-9000 (estimate) */
			appearances = new ArrayList<Appearance>();
			words.put(word, appearances);
		}
		
		appearances.add(new Appearance(book, chapter, verse)); // set constructed, so add to it
	}
	
	public List<Appearance> getAppearances(String word) {
		return words.get(word.toLowerCase());
	}
}
