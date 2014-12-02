package tests;

import static org.junit.Assert.*;
import main.StringUtils;

import org.junit.Test;

/**
 * Test class for String Utils.
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 *
 */
public class TestStringUtils {
	
	/**
	 * Asserts that punctuation is correctly removed from words.
	 */
	@Test
	public void testPunctuationRemove() {
		char[] word1chars = StringUtils.removePunctuation("word,").toCharArray();
		char[] word2chars = StringUtils.removePunctuation(",word").toCharArray();
		char[] word3chars = StringUtils.removePunctuation(",,,,,,word").toCharArray();
		char[] word4chars = StringUtils.removePunctuation(",,,,,,word.............").toCharArray();
		char[] word5chars = StringUtils.removePunctuation("word").toCharArray();

		for(int i = 0; i < word1chars.length; i++) {
			assertTrue(Character.isAlphabetic(word1chars[i]));
		}
		
		for(int i = 0; i < word2chars.length; i++) {
			assertTrue(Character.isAlphabetic(word2chars[i]));
		}
		
		for(int i = 0; i < word3chars.length; i++) {
			assertTrue(Character.isAlphabetic(word3chars[i]));
		}
		
		for(int i = 0; i < word4chars.length; i++) {
			assertTrue(Character.isAlphabetic(word4chars[i]));
		}
		
		for(int i = 0; i < word5chars.length; i++) {
			assertTrue(Character.isAlphabetic(word5chars[i]));
		}
			
	}
}
