package tests;


import static org.junit.Assert.assertEquals;

import java.util.List;

import main.WordAppearance;
import main.WordAppearance.DescriptionType;
import main.WordMap;

import org.junit.Test;

public class TestWordMap {

	@Test
	public void testAdding(){
		WordMap w = new WordMap(10, 1);
		w.addWord("Lord", "Harry Potter", 1, 2, DescriptionType.NONE);
		w.addWord("Water", "Harry Potter", 1, 2, DescriptionType.NONE);
		w.addWord("Water", "Harry Potter", 2, 2, DescriptionType.NONE);
		w.addWord("Water", "Harry Potter", 3, 20, DescriptionType.NONE);
		w.addWord("Happy", "Harry Potter", 5, 30, DescriptionType.NONE);
		w.addWord("Lord", "Lord of the rings", 8, 3, DescriptionType.NONE);
		
		List<WordAppearance> l = w.getAppearances("Water");
		
		for(int i = 0; i < l.size(); i++){
			assertEquals("Harry Potter", l.get(i).getBook());
			assertEquals((i)+1, l.get(i).getVerse());
		}
		
		List<WordAppearance> words = w.getAppearances("Lord");
		
		assertEquals(2, words.size());
	}
}
