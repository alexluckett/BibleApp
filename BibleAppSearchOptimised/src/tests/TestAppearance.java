package tests;

import static org.junit.Assert.*;
import main.Appearance;

import org.junit.Test;

public class TestAppearance {
	
	@Test
	public void testEquals() {
		/* same as each other. should return true. */
		Appearance a1 = new Appearance("book1", 1, 5);
		Appearance a2 = new Appearance("book1", 1, 5);
		
		assertTrue(a1.equals(a2));
	}
	
	@Test
	public void testNotEquals() {
		Appearance a1 = new Appearance("book2", 1, 5);
		Appearance a2 = new Appearance("book1", 1, 5);
		
		assertTrue(!a1.equals(a2));
	}
	
	@Test
	public void testNotEquals2() {
		Appearance a1 = new Appearance("book1", 5, 1);
		Appearance a2 = new Appearance("book1", 1, 5);
		
		assertTrue(!a1.equals(a2));
	}
	
}
