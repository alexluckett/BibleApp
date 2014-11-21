package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

/**
 * Main class for bible app. Will include ability to load in bible,
 * search for text and go to certain chapters/verses via command line interface.
 * 
 * @author Alex Luckett <lucketta@aston.ac.uk>
 * @author Ashley Bridgwood
 * @author Charandeep Rai
 *
 */
public class BibleApp {
	private File[] bookNames = (new File("data")).listFiles(); // retrieves a list of all files within the data folder (source files)

	private List<Book> parsedBooks = new ArrayList<Book>(66); // list of all fully parsed books
	private WordMap wordHistory = new WordMap();
	
	/**
	 * Constructs a new BibleApp. No content currently.
	 */	public BibleApp() {
		long startTime = System.currentTimeMillis();
		
		for(int j = 0; j < bookNames.length; j++) {
			readInFile(bookNames[j].getName());
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Read in time: " + (endTime - startTime) + " milliseconds.");
	}

	/**
	 * Runs a new instance of the bible app. Will (READ: not yet) take in search terms
	 * or "book chapter:verse" content locator.
	 *  
	 * @param args program arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		BibleApp bible = new BibleApp();
		bible.displayMenuSystem();
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

			String bookTitle = reader.readLine(); // first line is always the book title
			int verseNumber = 1;
			int chapterNumber = 0;

			Book book = new Book(bookTitle);
			Chapter chapter = null;
			
			String currentLine = reader.readLine();
			while(currentLine != null) {
				boolean isLineEmpty = currentLine.length() == 0;

				if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {
					if(chapterNumber != 0)
						book.addChapter(chapter);
					
					chapter = new Chapter(chapterNumber);
					chapterNumber++;
					verseNumber = 1; // new chapter, must reset verse count to 1
				} else if(!isLineEmpty && Character.isLetter(currentLine.trim().charAt(0))) {
					// description stuff
				} else if(!isLineEmpty) {
					chapter.addVerse(
						parseVerse(currentLine, bookTitle, verseNumber, chapterNumber)
					);
				}

				currentLine = reader.readLine();
			}

			parsedBooks.add(book);
			reader.close();
		} catch (Exception e) {
			System.err.println("Error reading bible files. Please ensure package is up to date.\n");
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays the menu screen and handles use choices
	 */
	public void displayMenuSystem() {
		Scanner sc = new Scanner(System.in);
		
		boolean finished = false;
		
		do {
			System.out.println("================================");
			System.out.println("| 1. Search for a word         |");
			System.out.println("| 2. Lookup Chapter            |");
			System.out.println("| 3. Lookup verse              |");
			System.out.println("| 4. Find verse by word        |");
			System.out.println("| 0. Exit                      |");
			System.out.println("================================");
			int userChoice = 0; 
			
			try {
				userChoice = sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Enter a valid number please.");
			}
		
			switch(userChoice) {
				case 0:
					System.out.println("Thank you for using BibleApp! (BibleApp Terminated)");
					finished = true;
					
					break;	
				case 1:
					search(getWordInformation(sc));
					
					break;
				case 2:
					lookupChapter(getBookInformation(sc), getChapterInformation(sc));
					
					break;
				case 3:
					lookupVerse(getBookInformation(sc), getChapterInformation(sc), getVerseInformation(sc));
					
					break;
				case 4:
					verseByWord(getWordInformation(sc));
					
					break;	
				
				default:
					System.out.println("Invalid option");
			}
			
		} while(!finished);
		
	}
	
	/**
	 * Get words from the user
	 */
	public String getWordInformation(Scanner sc) {
		System.out.println("What is the word or statement you are looking for: ");
		return sc.next();
	}	
	
	/**
	 * Get the book name from the user
	 */
	public String getBookInformation(Scanner sc) {
		System.out.println("Please enter the book name (With file extension): ");
		return sc.next();
	}
	
	/**
	 * Get chapter number from the user
	 */
	public int getChapterInformation(Scanner sc) {
		System.out.println("Please enter the chapter number: ");
		return sc.nextInt();
	}
	
	/**
	 * Get verse number from the user
	 */
	public int getVerseInformation(Scanner sc) {
		System.out.println("Please enter the verse number: ");
		return sc.nextInt();
	}
	
	/**
	 * Searches through the books, and returns the verse which the word is found in
	 */
	public void verseByWord(String statementToSearch){
		// Set<Appearance> appearances = wordHistory.getAppearances(statementToSearch); // this will return all the apperances of the search term. Case insensitive!
		// get verse content by using chapter and verse numbers from appearance
		// e.g: parsedBooks.get(appearance.getBook).getChapter(appearance.getChapter).getVerse(appearance.getVerse) or something like that. Should be quick!
	}
	
	/**
	 * Searches through books to find the verse which is needed
	 */
	public void lookupVerse(String bookName, int chapterNumber, int verseNumber) {
		
	}
	
	/**
	 * Searches through books to find the chapters which refer to the book and chapter number 
	 */
	public void lookupChapter(String bookName, int chapterNumber) {
		
	}
		

	/**
	 * Searches through books and finds occurrences of search terms.
	 * 
	 * @param statementToSearch search term
	 */
	public void search(String statementToSearch) {
		long start = System.currentTimeMillis();
		
		Set<Appearance> appearances = wordHistory.getAppearances(statementToSearch);
		
		if(appearances != null && appearances.size() != 0) {
			System.out.println("\"" + statementToSearch + "\" found! Occurances: " + appearances.size());
			
			for(Appearance appearance : appearances) {
				System.out.println(appearance.getBook() + " [" + appearance.getChapter() + ":" + appearance.getVerse() + "]");
			}
		} else {
			System.out.println("No search results found.\n");
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("TIME TAKEN TO SEARCH: " + (end - start) + "ms");
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
	private Verse parseVerse(String line, String bookName, int verseNumber, int chapterNumber) {
		String[] words = StringUtils.splitWords(line);
		
		//String[] words = line.split(' ');
		words[0] = null; // first word is number, so ignore it
		
		for(String word : words) {
			if(word != null)
				wordHistory.addWord(word, bookName, verseNumber, chapterNumber);
		}

		return new Verse(verseNumber, chapterNumber, line);
	}


}