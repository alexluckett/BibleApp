package main;

/**
 * Utility functions for Strings.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
 *
 */
public class StringUtils {
	
	/**
	 * Splits words up by spaces. Used as an alternative to string.split(" ") as a little faster (~50-100ms in some cases).
	 * 
	 * @param text
	 * @return String[] of words
	 */
	public static String[] splitWords(String line) {
		String[] words = new String[150]; // rough maximum words per verse 
		
		int count = 0;
		int i = 0;
		int previous = 0;
		
		while((i = line.indexOf(' ', previous)) != -1) {			
			String word = line.substring(previous, i);
			
			words[count++] = removePunctuation(word);
			
			previous = i+1;
		}
		
		words[count] = removePunctuation(line.substring(previous, line.length())); // last word!
		
		return words;
	}
	
	/**
	 * Strip punctuation from the end of a word. 
	 * Ensures that only words are logged, not punctuation also.
	 * 
	 * @param word
	 * @return word without end punctuation
	 */
	public static String removePunctuation(String word) {
		int wordLength = word.length() - 1;
		
		if(word.length() > 1 && !Character.isLetter(word.charAt(wordLength))) { // check the string has more than one letter, and if the last char is not a letter
			return word.substring(0, wordLength);
		}
		
		return word;
	}
}
