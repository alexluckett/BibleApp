package main;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Verse {
	private int verseNumber;
	private List<String> words = new LinkedList<String>();
	
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
