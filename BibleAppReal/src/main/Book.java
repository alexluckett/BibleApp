package main;

import java.util.Collection;
import java.util.LinkedList;

public class Book {
	private String title;
	private String description;
	private Collection<Chapter> chapters = new LinkedList<Chapter>();
	
	public Book(String title) {
		this.title = title;
		this.description = "";
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Collection<Chapter> getChapters() {
		return chapters;
	}
	
	public void addChapter(Chapter chapter) {
		chapters.add(chapter);
	}
	
}
