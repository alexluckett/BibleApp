package main;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int chapterNumber;
	private List<Verse> verses;
	
	public Chapter(int chapterNumber) {
		this.chapterNumber = chapterNumber;
		this.verses = new ArrayList<Verse>(100);
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public void addVerse(Verse verse) {
		verses.add(verse);
	}
	
	public Verse getVerse(int verseNumber) {
		return verses.get(verseNumber - 1); // 0 based numbering!
	}
	
	public List<Verse> getVerses() {
		return verses;
	}		
}