package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;


/**
 * Main class for bible app. Will include ability to load in bible,
 * search for text and go to certain chapters/verses via command line interface.
 * 
 * @author Alex Luckett
 * @author Ashley Bridgwood
 *
 */
public class BibleApp {
	public static final int TEST_RUNS = 1;

	private File[] bookNames = (new File("data")).listFiles(); // retrieves a list of all files within the data folder (source files)


	private List<Book> parsedBooks = new ArrayList<Book>(66); // list of all fully parsed books
	//private long wordCount = 0;
	/**
	 * Constructs a new BibleApp. No content currently.
	 */
	public BibleApp() { 
		for(int j = 0; j < bookNames.length; j++) {
			readInFile(bookNames[j].getName());
		}
	}

	/**
	 * Runs a new instance of the bible app. Will (READ: not yet) take in search terms
	 * or "book chapter:verse" content locator.
	 *  
	 * @param args program arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception { // TODO remove throws from method signature when speed issue resolved
		long startTime = System.currentTimeMillis();

		for(int i = 0; i < TEST_RUNS; i++) {
			BibleApp app = new BibleApp();
			
			app.search(args[0]);

			//System.out.println("Run " + i + ", verses: " + totalVerses);

			//System.out.println("Bible " + i + ", word count: " + app.getWordCount());

			//app.readInFile("2Kings.txt"); // 2 kings has a description. Here for testing purposes if needed..
			//app.readInFile("Psalms.txt"); // Psalms
		}

		long endTime = System.currentTimeMillis();

		System.out.println("Average time (" + BibleApp.TEST_RUNS + " run(s)): " + (endTime - startTime)/TEST_RUNS + " milliseconds\n");

		//throw new Exception("URGENT: This code simply cannot work properly. It takes ~26ms on my laptop (Alex)... which is ridiculously fast. What isn't being processed correctly?");
	}

	/**
	 * Processes a given file (book from the bible) using a filename passed through as
	 * a parameter. Assumes the file is in the /data package.
	 * 
	 * @param fileName filename of file to process (including .txt file extension)
	 */
	public void readInFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/" + fileName));
			//System.out.println("Processing: " + fileName);			

			Book book = new Book(reader.readLine()); // first line is always the book title

			int verseNumber = 1;
			int chapterNumber = 0;

			String currentLine = reader.readLine();

			while(currentLine != null) {
				boolean isLineEmpty = currentLine.length() == 0;

				if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {			
					chapterNumber++;
					verseNumber = 1; // new chapter, must reset verse count to 1
				} else if(!isLineEmpty && verseNumber == 1 && Character.isLetter(currentLine.charAt(0))) { // description is always before first verse starts and starts with letter
					/* 
					 * Do we need descriptions? are they used in the program anywhere?
					 * It's a waste of processing power to bother with them if we don't need
					 * 
					 * If not: this currently breaks for PSALM 119, which 
					 * has a weird name (description?) each 8 lines or so. How are we going to deal with this?
					 * 
					 * TODO: plan this section
					 * 
					 * pseudo code:
					 * 
					 * if(currentLine is a new book) {
					 * 	  book.setDescription(newLine);
					 * } else {
					 *    chapter.setDescription(newLine); // we're gonna need to bring chapters back if this is needed
					 * }
					 *
					 */
					//System.out.println("BOOK DESCRIPTION FOUND: " + currentLine); // this accidentally detects the first chapter description for psalm 119, as a result of it having verse 1
				} else if(!isLineEmpty && Character.isLetter(currentLine.charAt(0))) {
					//System.out.println("CHAPT. DESCRIPTION FOUND: " + currentLine); // chapter descriptions hit this condition (aside from the special case outlined above).
				} else if(!isLineEmpty) {
					book.addVerse(parseVerse(currentLine, verseNumber, chapterNumber));
					verseNumber++;
				}

				currentLine = reader.readLine();
			}

			parsedBooks.add(book);
			reader.close();
		} catch (Exception e) {
			System.err.println("Error reading bible files. Please ensure package is up to date.\n");
			//e.printStackTrace();
		}
	}

	/**
	 * Searches through books and finds occurrences of search terms.
	 * 
	 * @param statementToSearch search term
	 */
	public void search(String statementToSearch) {
		String bookName = "";
		int chapterNumber = -1;
		int verseNumber = -1;

		for(Book book : parsedBooks) {
			bookName = book.getTitle();

			Collection<Verse> verses = book.getVerses();

			for(Verse verse : verses) {
				verseNumber = verse.getVerseNumber();
				chapterNumber = verse.getChapterNumber();

				Collection<String> words = verse.getWords();

				for(String word : words) {
					if(word.contains(statementToSearch))
						System.out.println("\"" + statementToSearch + "\" found. Book: " + bookName + ", chapter: " + chapterNumber + ", verse: " + verseNumber);
				}
			}

			if(verseNumber < 0 || chapterNumber < 0)
				System.out.println("Search term not found.");
		}
	}

	/**
	 * Parses a verse, given the verse (line), verse number and chapter number.
	 * 
	 * @param line verse to process
	 * @param verseNumber number of this verse
	 * @param chapterNumber chapter number of this verse
	 * 
	 * @return Verse object - completed verse
	 */
	private Verse parseVerse(String line, int verseNumber, int chapterNumber) {
		String verseContent = line.substring(line.indexOf(" ")+1); // remove verse number (always first string - we already know it at this point

		//wordCount += verseContent.split(" ").length;

		Verse verse = new Verse(verseNumber, chapterNumber);
		verse.addWord(verseContent);

		/* debugging only - prints out contents of parsed verse.
		 * 
		System.out.println("Verse " + verseNumber + " parsed.");
		System.out.println("Verse content: ");
		for(String word : verse.getWords()) {
			System.out.println(word + " ");
		}
		System.out.println();
		System.out.println();
		 */

		return verse;
	}

}