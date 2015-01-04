package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	 * HashMap is O(1) retrieval time. 12798 total words - initialise with rounded value for better performance.
	 * 1 in constructor is the load factor to determine when to double in size - as it's 1, it means it won't double until 12800.
	 * 
	 * Using a List implementation to hold the appearances because we need to keep ordering. Most appropriate collection, since
	 * a word can appear multiple times within a verse - therefore a Set would be inappropriate.
	 */
	private Map<String, List<WordAppearance>> words;
	
	public WordMap(int maxWordsEstimate, int loadFactor) {
		words = new HashMap<String, List<WordAppearance>>(maxWordsEstimate, loadFactor);
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
		List<WordAppearance> appearances = getAppearances(word); // search is not case sensitive
		
		word = word.toLowerCase();
		
		if(appearances == null) { // no appearances have been logged
			
			/* Don't initialise ArrayList with an initial value! This decreases performance since
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
	 * @return int size of WordMap
	 */
	public int uniqueWords() {
		return words.size();
	}
	
	/**
	 * Returns the total number of words within the entire WordMap.
	 * 
	 * WORD COUNT EXCLUDES CERTAIN THINGS, SUCH AS "Chapter X/PSALM X" AND VERSE NUMBERS, etc.
	 * 
	 * @return totalWords
	 */
	public int totalWords() {
		int wordCount = 0;
		
		for(Map.Entry<String, List<WordAppearance>> currentEntry : words.entrySet())
			wordCount += currentEntry.getValue().size();
		
		return wordCount;
	}
}
