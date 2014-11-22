package main;

/**
 * Represents an appearance of a search term.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
 */
public class Appearance {
	private String book;
	private int chapter;
	private int verse;
	
	public Appearance(String book, int chapter, int verse) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
	}
	
	public String getBook() {
		return book;
	}
	
	public int getChapter() {
		return chapter;
	}
	
	public int getVerse() {
		return verse;
	}
	
	@Override
	public int hashCode() {
		int hash = 42;
		
		hash = hash * 55 + book.hashCode();
		hash = hash * 55 + chapter;
		hash = hash * 55 + verse;
		
		return hash;
	}
	
	/**
	 * Checks if two appearances are equal
	 */
	@Override
	public boolean equals(Object obj) {
		Appearance comparison;
		
		if(!(obj instanceof Appearance)) { // can't be equal if they're not the same type!
			return false;
		} else {
			comparison = (Appearance) obj;
		}
		
		return hashCode() == comparison.hashCode();
	}
}
