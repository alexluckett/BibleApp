package main;

import java.util.Collection;

public class Verse {
	private int verseNumber;
	private Collection<String> words;
	
	public Verse(int verseNumber) {
		this.verseNumber = verseNumber;
	}
	
	public void addWord(String word) {
		words.add(word);
	}
	
	public int getVerseNumber() {
		return verseNumber;
	}
	
	public Collection<String> getWords() {
		return words;
	}
	
}
