package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Appearance.DescriptionType;

/**
 * Maps a list of words to a set of appearances. Used for quick searching.
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class WordMap {
	private Map<String, List<Appearance>> words = new HashMap<String, List<Appearance>>(); // hashmap is O(1) retrieval time
	
	public void addWord(String word, String book, int verse, int chapter, DescriptionType descriptionType) {		
		word = word.toLowerCase();
		
		List<Appearance> appearances = getAppearances(word); // search is not case sensitive
		
		if(appearances == null) { // no appearances have been logged
			/* Don't initialise with an initial value! This decreases performance since
			 *  the actual word count could range anywhere between 0-9000 (estimate) */
			appearances = new ArrayList<Appearance>();
			words.put(word, appearances);
		}
		
		appearances.add(new Appearance(book, chapter, verse, descriptionType)); // set constructed, so add to it
	}
	
	public List<Appearance> getAppearances(String word) {
		return words.get(word.toLowerCase());
	}
}
