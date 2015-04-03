package main;

/**
 * Utility functions for Strings.
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class StringUtils {
	
	/**
	 * Splits words up by spaces, 150 words max.
	 * 
	 * Used as an alternative to string.split(" ") as a little faster (~50-100ms in some cases).
	 * 
	 * @param text
	 * @return String[] of words
	 */
	public static String[] splitWords(String line) {
		return splitWords(line, 150);
	}
	
	/**
	 * Splits words up by spaces. Used as an alternative to string.split(" ") as a little faster (~50-100ms in some cases).
	 * 
	 * @param text
	 * @return String[] of words
	 */
	public static String[] splitWords(String line, int maxWords) {
		String[] words = new String[maxWords]; // rough maximum words per line 
		
		int count = 0;
		int i = 0;
		int previous = 0;
		
		while((i = line.indexOf(' ', previous)) != -1) { // check the position of the next space for our new word. -1 is no more available.		
			words[count++] = removePunctuation(line.substring(previous, i)); // get word between last word and new
			
			previous = i + 1;
		}
		
		words[count] = removePunctuation(line.substring(previous, line.length())); // last word!
		
		return words;
	}
	
	/**
	 * Strip punctuation from the start and end of a word. 
	 * Ensures that only words are logged, not punctuation also.
	 * 
	 * @param word
	 * @return word without end punctuation
	 */
	public static String removePunctuation(String word) {
		int wordStart = 0;
		int wordLength = word.length();
		boolean changed = false;
		
		if(word.length() > 2 && !Character.isLetter(word.charAt(wordStart))) {
			wordStart++; // remove any symbols at start of words
			changed = true;
		}
		
		if(word.length() > 1 && !Character.isLetter(word.charAt(wordLength-1))) { // check the string has more than one letter, and if the last char is not a letter
			wordLength = wordLength - 1;
			changed = true;
		}
		
		if(changed)
			return removePunctuation(word.substring(wordStart, wordLength)); // recursively remove 
		else
			return word;
	}
}
