package main;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private final int chapterNumber;
	private List<Verse> verses;
	
	public Chapter(int chapterNumber) {
		this.chapterNumber = chapterNumber;
		this.verses = new ArrayList<Verse>(50);
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public void addVerse(Verse verse) {
		if(verse == null)
			throw new IllegalArgumentException("Verse number cannot be null.");
		
		verses.add(verse);
	}
	
	public Verse getVerse(int verseNumber) {
		if(verseNumber == 0 || verseNumber > verses.size())
			throw new IllegalArgumentException("Verse number cannot be less than 1.");
		
		return verses.get(verseNumber - 1); // 0 based numbering!
	}
	
	public List<Verse> getVerses() {
		return verses;
	}		
}