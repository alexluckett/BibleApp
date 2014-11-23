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
 * @author Ashley Bridgwood <bridgwa1@aston.ac.uk>
 * @author Charandeep Rai <raics@aston.ac.uk>
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

			Book book = new Book(fileName, bookTitle);
			Chapter chapter = null;
			
			String currentLine;
			
			while((currentLine = reader.readLine()) != null) {
				boolean isLineEmpty = currentLine.length() == 0;

				if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {
					if(chapterNumber != 0)
						book.addChapter(chapter);
					
					chapter = new Chapter(chapterNumber++);
					verseNumber = 1; // new chapter, must reset verse count to 1
				} else if(!isLineEmpty && Character.isLetter(currentLine.trim().charAt(0))) {
					// found a description, so ignore it. not part of spec.
				} else if(!isLineEmpty) {
					chapter.addVerse(
						parseVerse(currentLine, bookTitle, verseNumber, chapterNumber)
					);
				}

				
			}

			parsedBooks.add(book);
			reader.close();
		} catch (Exception e) {
			System.err.println("Error reading bible files. Please ensure package is up to date.\n");
			//e.printStackTrace();
		}
	}
	
	/**
	 * Displays the menu screen and handles use choices
	 */
	public void displayMenuSystem() {
		Scanner sc = new Scanner(System.in);
		
		boolean finished = false;
		
		do {
			StringBuilder menu = new StringBuilder();
			{
				menu.append("================================\n");
				menu.append("| 1. Search for a word         |\n");
				menu.append("| 2. Lookup Chapter            |\n");
				menu.append("| 3. Lookup verse              |\n");
				menu.append("| 4. Find verse by word        |\n");
				menu.append("| 0. Exit                      |\n");
				menu.append("================================\n");
				menu.append("Please enter the activity number you wish to use: ");
			}
			System.out.print(menu);
			
			int userChoice = 0; 
			
			try {
				userChoice = sc.nextInt();
			} catch (Exception e) {
				System.err.println("Invalid option.");
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
					lookupChapter(getBookIdFromUser(sc), getChapterInformation(sc));
					
					break;
				case 3:
					lookupVerse(getBookIdFromUser(sc), getChapterInformation(sc), getVerseInformation(sc));
					
					break;
				case 4:
					verseByWord(getWordInformation(sc));
					
					break;	
				
				default:
					System.out.println("\nInvalid option!");
			}
			
		} while(!finished);
		
	}
	
	/**
	 * Get words from the user
	 */
	public String getWordInformation(Scanner sc) {
		System.out.println("Please enter the word you are looking for: ");
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
	 * Display the book selection to the user 
	 */
	public int getBookIdFromUser(Scanner sc) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < parsedBooks.size(); i++){
			sb.append((i+1) + ": " + parsedBooks.get(i).getTitle() + "\n");
		}
		
		System.out.println("Please enter the book number:");
		System.out.println(sb);
		System.out.println("Please choose a book number: ");
		
		return sc.nextInt() - 1; // take into account 0 based numbering
	}
	
	/**
	 * Searches through the books, and returns the verse which the word is found in
	 */
	public void verseByWord(String statementToSearch){
		StringBuilder sb = new StringBuilder(); 
		long starttime = System.currentTimeMillis();
	
		 List<Appearance> searchWord = wordHistory.getAppearances(statementToSearch); 
		 if(searchWord != null){
			sb.append(statementToSearch + " found!" +  "\n");
						
			for(Appearance appearance : searchWord) {
		sb.append(appearance.getBook() + " [" + appearance.getChapter() + ":" + appearance.getVerse() + "] \n");
			
			//searchWord = sb.append(parsedBooks.get(appearance.getBook()) .getChapter(appearance.getChapter()) .getVerse(appearance.getVerse())); 
		 } 
		 }else{
			 sb.append("Word not found!");
		 }
		
			
		long endtime = System.currentTimeMillis();
		System.out.println(sb);
		System.out.println("Time Taken: " + ( endtime - starttime ) + " milliseconds");
		//Set<Appearance> appearances = wordHistory.getAppearances(statementToSearch); // this will return all the apperances of the search term. Case insensitive!
		// get verse content by using chapter and verse numbers from appearance
		// e.g: parsedBooks.get(appearance.getBook).getChapter(appearance.getChapter).getVerse(appearance.getVerse) or something like that. Should be quick!
	}
	
	/**
	 * Searches through books to find the verse which is needed
	 */
	public void lookupVerse(int bookId, int chapterNumber, int verseNumber) {
		long startTime = System.currentTimeMillis();
		
		Verse answer = parsedBooks.get(bookId).getChapter(chapterNumber).getVerse(verseNumber);
		long endTime = System.currentTimeMillis();
		
		System.out.println("Verse: " + answer.getText());
		System.out.println("Time taken: " + (endTime - startTime) + " ms");
	}
	
	/**
	 * Searches through books to find the chapters which refer to the book and chapter number 
	 */
	public void lookupChapter(int bookId, int chapterNumber) {
		StringBuilder sb = new StringBuilder();
		
		long startTime = System.currentTimeMillis();
		
		Chapter answer = parsedBooks.get(bookId).getChapter(chapterNumber);
		List<Verse> v = answer.getVerses();
		
		for(Verse verse : v) {
			sb.append(verse.getText() + "\n");
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println(sb);
		System.out.println("Time Taken: " + (endTime - startTime) + " ms");
	}
		

	/**
	 * Searches through books and finds occurrences of search terms.
	 * 
	 * @param statementToSearch search term
	 */
	public void search(String statementToSearch) {
		StringBuilder sb = new StringBuilder();
		
		long start = System.currentTimeMillis();
		
		List<Appearance> appearances = wordHistory.getAppearances(statementToSearch);
		
		if(appearances != null && appearances.size() != 0) {
			sb.append("\"" + statementToSearch + "\" found! Occurances: " + appearances.size() + "\n");
			
			for(Appearance appearance : appearances) {
				sb.append(appearance.getBook() + " [" + appearance.getChapter() + ":" + appearance.getVerse() + "] \n"); // repeated system outs are incredibly slow. this gives much better performance.
			}
			
			sb.append("\"" + statementToSearch + "\" found! Occurances: " + appearances.size() + "\n");
		} else {
			sb.append("No search results found.\n");
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("\n" + sb);
		System.out.println("TIME TAKEN TO SEARCH AND PRINT: " + (end - start) + "ms");
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
		words[0] = null; // first word is number, so ignore it
		
		for(String word : words) {
			if(word != null)
				wordHistory.addWord(word, bookName, verseNumber, chapterNumber);
		}

		return new Verse(verseNumber, chapterNumber, line);
	}
}