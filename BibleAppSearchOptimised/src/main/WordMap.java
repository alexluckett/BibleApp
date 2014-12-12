package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static main.Constants.*;

import main.WordAppearance.DescriptionType;

/**
 * Maps a list of case insensitive words to a set of appearances. Used for quick searching.
 * 
 * Wrapper class for a HashMap, holding a String key (the word) and a List<WordAppearance> value (where it appears).
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class WordMap {
	/**
	 * Hashmap is O(1) retrieval time. 12798 total words - initialise with rounded value for better performance.
	 * 1 in constructor is the load factor to determine when to double in size - as it's 1, it means it won't double until 12800.
	 */
	private Map<String, List<WordAppearance>> words;
	
	public WordMap() {
		words = new HashMap<String, List<WordAppearance>>(TOTAL_WORDS, TOTAL_WORDS_LOADFACTOR);
	}
	
	/**
	 * Adds a word into the WordMap. Logs an appearance of a word within a book, chapter and verse. Can be used to quickly
	 * find where a word appears within the bible.
	 * 
	 * Also takes in a DesscriptionType enum to represent if the current word is part of a description. If so, the enum will
	 * specify which part or DescriptionType.NONE for no description.
	 * 
	 * @param word to log
	 * @param book where word appears
	 * @param verse where word appears
	 * @param chapter where word appears
	 * @param descriptionType type of description word is part of (DescriptionType.NONE if not)
	 */
	public void addWord(String word, String book, int verse, int chapter, DescriptionType descriptionType) {		
		word = word.toLowerCase();
		
		List<WordAppearance> appearances = getAppearances(word); // search is not case sensitive
		
		if(appearances == null) { // no appearances have been logged
			
			/* Don't initialise with an initial value! This decreases performance since
			 *  the actual word count could range anywhere between 0-9000 (estimate) */
			appearances = new ArrayList<WordAppearance>();
			words.put(word, appearances);
		}
		
		appearances.add(new WordAppearance(book, chapter, verse, descriptionType)); // set constructed, so add to it
	}
	
	/**
	 * Get a list of appearances for a specific word. Case insensitive.
	 * 
	 * @param word - search term
	 * @return List<Appearances> where the word appears
	 */
	public List<WordAppearance> getAppearances(String word) {
		return words.get(word.toLowerCase());
	}
	
	/**
	 * Returns the amount of unique words (case insensitive) held within the WordMap.
	 * 
	 * @return int size of wordmap
	 */
	public int size() {
		return words.size();
	}
}
