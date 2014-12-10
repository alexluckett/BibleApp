package main;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private final String title;
	private final String fileName;
	private String description;
	private List<Chapter> chapters = new ArrayList<Chapter>(10); // need to keep order
	
	public Book(String fileName, String title) {
		this.title = title;
		this.fileName = fileName;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getFileName(){
		return fileName;
	}
		
	public void addChapter(Chapter chapter) {
		if(chapter == null)
			throw new IllegalArgumentException("Chapter number cannot be null.");
		
		chapters.add(chapter);
	}
	
	public Chapter getChapter(int chapterNumber) {
		if(chapterNumber > chapters.size())
			throw new IllegalArgumentException("Chapter number is invalid (too big/small).");
		
		return chapters.get(chapterNumber-1);
	}
	
	public List<Chapter> getChapters() {
		return chapters;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
