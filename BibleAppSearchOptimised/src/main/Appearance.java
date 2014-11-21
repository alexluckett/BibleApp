package main;

public class Appearance {
	private String book;
	private int chapter;
	private int verse;
	
	public Appearance(String book, int chapter, int verse) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
	}
	
	public String getBook() {
		return book;
	}
	
	public int getChapter() {
		return chapter;
	}
	
	public int getVerse() {
		return verse;
	}
	
	@Override
	public int hashCode() {
		return (book.hashCode() + chapter + verse);
	}
	
	@Override
	public boolean equals(Object obj) {
		Appearance comparison;
		
		if(!(obj instanceof Appearance)) {
			return false;
		} else {
			comparison = (Appearance) obj;
		}
		
		return hashCode() == comparison.hashCode();
	}
}
