package main;

import java.util.*;

/**
 * Maps a list of words to a set of appearances. Used for quick searching.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
 */
public class WordMap {
	private Map<String, Set<Appearance>> words = new HashMap<String, Set<Appearance>>(); // hashmap is O(1) retrieval time
	
	public WordMap() { }
	
//	public void addWord(String word, String book, int verse, int chapter) {
//		word = word.toLowerCase(); // makes searching case insensistive
//		Appearance appearance = new Appearance(book, chapter, verse);
//		
//		if(words.get(word) == null) { // no appearances have been logged
//			HashSet<Appearance> appearances = new HashSet<Appearance>();
//			appearances.add(appearance);
//			
//			words.put(word, appearances);
//		} else { // already have appearances, so just add to that
//			Set<Appearance> appearances = words.get(word);
//			appearances.add(appearance);
//			
//			words.put(word, appearances);
//		}
//	}
	
	public void addWord(String word, String book, int verse, int chapter) {
		word = word.toLowerCase(); // search is not case sensitive
		
		Set<Appearance> appearances = words.get(word);
		
		if(appearances == null) { // no appearances have been logged
			appearances = new HashSet<Appearance>();
			words.put(word, appearances);
		}
		
		appearances.add(new Appearance(book, chapter, verse)); // set constructed, so add to it
	}
	
	public Set<Appearance> getAppearances(String word) {
		return words.get(word.toLowerCase());
	}
}
