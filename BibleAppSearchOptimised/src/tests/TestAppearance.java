package tests;

import static org.junit.Assert.*;
import main.WordAppearance;
import main.WordAppearance.DescriptionType;

import org.junit.Test;

public class TestAppearance {
	
	@Test
	public void testEquals() {
		/* same as each other. should return true. */
		WordAppearance a1 = new WordAppearance("book1", 1, 5, DescriptionType.NONE);
		WordAppearance a2 = new WordAppearance("book1", 1, 5, DescriptionType.NONE);
		
		assertTrue(a1.equals(a2));
	}
	
	@Test
	public void testNotEquals() {
		WordAppearance a1 = new WordAppearance("book2", 1, 5, DescriptionType.NONE);
		WordAppearance a2 = new WordAppearance("book1", 1, 5, DescriptionType.NONE);
		
		assertTrue(!a1.equals(a2));
	}
	
	@Test
	public void testNotEquals2() {
		WordAppearance a1 = new WordAppearance("book1", 5, 1, DescriptionType.NONE);
		WordAppearance a2 = new WordAppearance("book1", 1, 5, DescriptionType.NONE);
		
		assertTrue(!a1.equals(a2));
	}
	
	@Test
	public void testNotEqualsEnum() {
		WordAppearance a1 = new WordAppearance("book1", 1, 1, DescriptionType.BOOK);
		WordAppearance a2 = new WordAppearance("book1", 1, 1, DescriptionType.NONE);
		
		assertTrue(!a1.equals(a2));
	}
	
}
