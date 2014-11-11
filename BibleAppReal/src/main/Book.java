package main;

import java.util.Collection;
import java.util.LinkedList;

public class Book {
	private final String title;
	private Collection<Verse> verses = new LinkedList<Verse>();
	
	public Book(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Collection<Verse> getVerses() {
		return verses;
	}
	
	public void addVerse(Verse chapter) {
		verses.add(chapter);
	}
	
}
