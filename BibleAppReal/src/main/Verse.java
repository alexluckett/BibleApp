package main;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Verse {
	private final int chapterNumber;
	private final int verseNumber;
	private List<String> words = new LinkedList<String>();
	
	public Verse(int verseNumber, int chapterNumber) {
		this.verseNumber = verseNumber;
		this.chapterNumber = chapterNumber;
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	public int getVerseNumber() {
		return verseNumber;
	}
	
	public int getChapterNumber() {
		return chapterNumber;
	}
	
	public Collection<String> getWords() {
		return words;
	}
	
}
