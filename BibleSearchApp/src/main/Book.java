package main;

import java.util.ArrayList;
import java.util.List;
import static main.Constants.*;

/**
 * Represents a book object within the bible. 
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class Book {
	private final String title;
	private final String fileName;
	private String description;
	private List<Chapter> chapters; // need to keep ordering
	
	public Book(String fileName, String title) {
		this.title = title;
		this.fileName = fileName;
		chapters = new ArrayList<Chapter>(CHAPTERS_INITIAL_AMOUNT);
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
