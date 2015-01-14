package main;

/**
 * Represents an appearance of a search term.
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class WordAppearance {
	private final String book;
	private final int chapter;
	private final int verse;
	private final DescriptionType descriptionType;
	
	public WordAppearance(String book, int chapter, int verse, DescriptionType descriptionType) {
		this.book = book;
		this.chapter = chapter;
		this.verse = verse;
		this.descriptionType = descriptionType;
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
	
	public DescriptionType descriptionType() {
		return descriptionType;
	}
	
	@Override
	public int hashCode() {
		int hash = book.hashCode();
		hash = hash * 55 + chapter;
		hash = hash * 55 + verse;
		hash = hash + descriptionType.hashCode();
		
		return hash;
	}
	
	/**
	 * Checks if two appearances are equal
	 */
	@Override
	public boolean equals(Object obj) {
		WordAppearance comparison;
		
		if(obj instanceof WordAppearance) {
			comparison = (WordAppearance) obj;
		} else {
			return false; // can't be equal if they're not the same type!
		}
		
		return hashCode() == comparison.hashCode();
	}
	
	@Override
	public String toString() {
		String toString;
		
		if(descriptionType() == DescriptionType.NONE) {
			toString = getBook() + " [" + getChapter() + ":" + getVerse() + "] \n"; // inform user what book, chapter and verse the appearance is within
		} else if(descriptionType() == DescriptionType.CHAPTER) {
			toString = getBook() + " [" + getChapter() + ": CHAPTER/PSALM DESCRIPTION]\n"; // indicate to user search term is within chapter/psalm description
		} else {
			toString = getBook() + ": BOOK DESCRIPTION\n"; // indicate to user search term is within book description
		}
		
		return toString;
	}
	
	/**
	 * Represents the type of description to log. 
	 *  
	 * @author Alexander Luckett <lucketta@aston.ac.uk>
	 */
	public enum DescriptionType {
		NONE, BOOK, CHAPTER
	}
}
