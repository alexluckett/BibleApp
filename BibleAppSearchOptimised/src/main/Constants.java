package main;

/**
 * Constants to be used within the program
 * 
 * @author Alexander Luckett <lucketta@aston.ac.uk>
 */
public class Constants {
	/**
	 * Maximum number of books in the bible. Used to initialise collection holding books.
	 * 
	 * 66 books in total.
	 */
	public static final int MAX_BOOKS = 66;
	
	/**
	 * Initial number of chapters to initialise collections with.
	 */
	public static final int CHAPTERS_INITIAL_AMOUNT = 10;
	
	/**
	 * Initial number of verses to initialise collections with.
	 * 
	 * Testing showed ~50 verses was more efficient in unbounded collections.
	 */
	public static final int VERSES_INITIAL_AMOUNT = 50;
	
	/**
	 * Total unique words within the bible text files. Used to initialise the WordMap for efficiency.
	 */
	public static final int TOTAL_WORDS = 12800;
	
	/**
	 * Size as a decimal (0-1) in which the WordMap should double in capacity. 
	 * 
	 * Since we know how many total words are in the bible (@see TOTAL_WORDS), 
	 * we can wait until the collection is full before expanding.
	 */
	public static final int TOTAL_WORDS_LOADFACTOR = 1;
}
