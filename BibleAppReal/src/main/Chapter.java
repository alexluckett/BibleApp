package main;

import java.util.LinkedList;
import java.util.List;

public class Chapter {
	private int chapterNumber;
	private List<Verse> verses;
	
	public Chapter(int chapterNumber) {
		this.chapterNumber = chapterNumber;
		this.verses = new LinkedList<Verse>();
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public List<Verse> getVerses() {
		return verses;
	}
	
	public void addVerse(Verse verse) {
		verses.add(verse);
	}
		
}