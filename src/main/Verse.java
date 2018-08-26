package main;

/**
 * Represents a verse object within a chapter.
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class Verse {
	private final int verseNumber;
	private String text;
	
	public Verse(int verseNumber, String text) {
		this.verseNumber = verseNumber;
		this.text = text;
	}
	
	public int getVerseNumber() {
		return verseNumber;
	}
	
	public String getText() {
		return text;
	}
}
