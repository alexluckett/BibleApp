package main;

/**
 * Utility functions for Strings.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
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
		String[] words = new String[maxWords]; // rough maximum words per verse 
		
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
		int wordLength = word.length() - 1;
		
		if(word.length() > 2 && !Character.isLetter(word.charAt(wordStart)))
			wordStart++; // remove any symbols at start of words
		
		if(word.length() > 1 && !Character.isLetter(word.charAt(wordLength))) { // check the string has more than one letter, and if the last char is not a letter
			return word.substring(wordStart, wordLength);
		}
		
		return word.substring(wordStart);
	}
}
