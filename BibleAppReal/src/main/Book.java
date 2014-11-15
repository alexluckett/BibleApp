package main;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Book {
	private final String title;
	private final String fileName;
	private Collection<Verse> verses = new LinkedList<Verse>();
	
	public Book(String fileName, String title) {
		this.title = title;
		this.fileName = fileName;
	}
	
	public String getFilename(){
		return fileName;
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
	
	public LinkedHashSet<Integer> getChapters() {
		LinkedHashSet<Integer> chapterNumbers = new LinkedHashSet<Integer>();
		
		for(Verse verse : verses) {
			chapterNumbers.add((verse.getChapterNumber()));
		}
		
		return chapterNumbers;
	}
	
}
