package main;

import java.util.Collection;

public class Chapter {
	private String title;
	private String description;
	private Collection<Verse> verses;
	
	public Chapter(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public Collection<Verse> getVerses() {
		return verses;
	}
	
}
