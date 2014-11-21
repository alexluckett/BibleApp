package main;

public class Verse {
	private final int chapterNumber;
	private final int verseNumber;
	private String text;
	
	public Verse(int verseNumber, int chapterNumber, String text) {
		this.verseNumber = verseNumber;
		this.chapterNumber = chapterNumber;
		this.text = text;
	}
	
	public int getVerseNumber() {
		return verseNumber;
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public String getText() {
		return text;
	}
}
