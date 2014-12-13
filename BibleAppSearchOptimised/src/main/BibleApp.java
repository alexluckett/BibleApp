package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import static main.Constants.*;

import main.WordAppearance.DescriptionType;

/**
 * Main class for bible app. Will include ability to load in bible,
 * search for text and go to certain chapters/verses via command line interface.
 * 
 * @author Alexander Luckett  <lucketta@aston.ac.uk>
 * @author Ashley Bridgwood   <bridgwa1@aston.ac.uk>
 * @author Charandeep Rai     <raics@aston.ac.uk>
 *
 */
public class BibleApp {
	private List<Book> parsedBooks = new ArrayList<Book>(MAX_BOOKS); // list of all fully parsed books
	private WordMap wordIndex = new WordMap();

	/**
	 * Constructs a new BibleApp. No content currently.
	 */
	public BibleApp() {
		File[] bookNames = null;
		
		try {
			bookNames = new File(this.getClass().getResource("../data/").getFile()).listFiles();
		} catch (Exception e) { } // don't need to handle this -> error printed out and program terminated later
		
		long startTime = System.currentTimeMillis();

		if(bookNames != null && bookNames.length > 0) {
			for(int j = 0; j < bookNames.length; j++) {
				readInFile(bookNames[j].getAbsolutePath());
			}

			long endTime = System.currentTimeMillis();

			System.out.println("Read in time: " + (endTime - startTime) + " milliseconds.");
			System.out.println("Total unique words (case insensitive): " + wordIndex.size());
		} else {
			System.err.println("\"data\" subdirectory is empty or does not exist. Please create directory with text files within.\n");
		}

	}

	/**
	 * Runs a new instance of the bible app. Will (READ: not yet) take in search terms
	 * or "book chapter:verse" content locator.
	 *  
	 * @param args program arguments
	 * @throws Exception 
	 */
	public static void main(String[] args) {
		new BibleApp().displayMenuSystem();
	}

	/**
	 * Processes a given file (book from the bible) using a filename passed through as
	 * a parameter. Assumes the file is in the /data package.
	 * 
	 * @param fileName filename of file to process (including .txt file extension)
	 */
	public void readInFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String bookTitle = reader.readLine(); // first line is always the book title
			int verseNumber = 1;
			int chapterNumber = 0;

			Book book = new Book(fileName, bookTitle);
			Chapter chapter = null;

			String currentLine;

			while((currentLine = reader.readLine()) != null) {
				if(currentLine.isEmpty())
					continue; // don't care if line is empty - go to next iteration

				if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {
					if(chapterNumber != 0)
						book.addChapter(chapter);

					chapter = new Chapter(chapterNumber++);
					verseNumber = 1; // new chapter, must reset verse count to 1
				} else if(Character.isLetter(currentLine.trim().charAt(0))) { // condition met if is a description
					if(chapterNumber == 0) {
						book.setDescription(currentLine); // haven't processed a chapter yet, so it must be the book description
						parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.BOOK);
					} else {
						chapter.setDescription(currentLine);
						parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.CHAPTER);
					}
				} else {
					chapter.addVerse(parseLine(currentLine, bookTitle, verseNumber, chapterNumber, DescriptionType.NONE));
					verseNumber++;
				}
			}

			book.addChapter(chapter); // add last book of last chapter manually -> can't auto detect because no new line that starts with "CHAPTER/PSALM"
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
				menu.append("| 5. Find range of verses      |\n");
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
				displayMenuSystem();
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
			case 5:
				rangeOfVerses(getBookIdFromUser(sc), getChapterInformation(sc), getVerseRangeInformation(sc));
				break;

			default:
				System.out.println("\nInvalid option!");
			}

		} while(!finished);

	}

	/**
	 * Get a range of verse from the user
	 */
	public String getVerseRangeInformation(Scanner sc){
		System.out.println("Please enter the range of verses you would like (EG: 1-5):");
		
		return sc.next();
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
		
		int chapter = -1;
		
		try {
			chapter = sc.nextInt();
		} catch (InputMismatchException e) {
			chapter = -1;
		}
		
		return chapter;
	}

	/**
	 * Get verse number from the user
	 */
	public int getVerseInformation(Scanner sc) {
		System.out.println("Please enter the verse number: ");
		
		int verse = -1;
		
		try {
			verse = sc.nextInt();
		} catch (InputMismatchException e) {
			verse = -1;
		}
		
		return verse;
	}

	/**
	 * Display the book selection to the user 
	 */
	public int getBookIdFromUser(Scanner sc) {
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < parsedBooks.size(); i++){
			sb.append((i+1) + ": " + parsedBooks.get(i).getTitle() + "\n"); // print out a list of book titles, numbered 1-n
		}

		System.out.println(sb);
		System.out.println("Please choose a book number: ");

		int menuItem = -1;
		
		try {
			menuItem = sc.nextInt() - 1; // take into account 0 based numbering

			if(menuItem < 0 || menuItem > parsedBooks.size())
				menuItem = -1;
		} catch (Exception e) {
			menuItem = -1;
		}

		return menuItem; 
	}

	/**
	 * Get verses from a range of verse numbers
	 */
	public void rangeOfVerses(int bookId, int chapterNumber, String verses) {
		boolean midFound = false;
		String startingNumber = "";
		String endingNumber = "";
		
		for(int i = 0; i < verses.length(); i++){
			if(verses.substring(i, i+1).equalsIgnoreCase("-")){
				midFound = true;
			}
			
			if(midFound == false){
				startingNumber += verses.substring(i, i+1);
			}
		
			if(midFound == true && !(verses.substring(i, i+1).equalsIgnoreCase("-"))){
				endingNumber += verses.substring(i, i+1);
			}
		}

		int startVerseNumber = Integer.parseInt(startingNumber);
		int endVerseNumber = Integer.parseInt(endingNumber);

		StringBuilder sb = new StringBuilder();
		long startTime = System.currentTimeMillis();

		try {
			for(int i = startVerseNumber; i <= endVerseNumber; i++)
				sb.append(parsedBooks.get(bookId).getChapter(chapterNumber).getVerse(i-1).getText() + "\n");
		} catch (Exception e) {
			sb.append("Book/verse/chapter does not exist.");
		}

		long endTime = System.currentTimeMillis();
		System.out.println(sb);
		System.out.println("Time taken: " + (endTime - startTime) + " ms");
	}

	/**
	 * Searches through the books, and returns the verse which the word is found in
	 */
	public void verseByWord(String statementToSearch) {
		StringBuilder sb = new StringBuilder(); 
		long starttime = System.currentTimeMillis();

		List<WordAppearance> searchWord = wordIndex.getAppearances(statementToSearch);  // list of appearances for this search term

		if(searchWord != null){
			sb.append(statementToSearch + " found!" +  "\n");

			for(WordAppearance appearance : searchWord) {
				int bookId = 0;
				
				for(int appearanceId = 0; appearanceId < parsedBooks.size(); appearanceId++){ // get book Id from the search term
					if(parsedBooks.get(appearanceId).getTitle().equals(appearance.getBook())){
						bookId = appearanceId;
					}
				}
				
				sb.append(appearance.getBook() + " [" + appearance.getChapter() + ":" + appearance.getVerse() + "]: "
						+ parsedBooks.get(bookId).getChapter(appearance.getChapter()).getVerse(appearance.getVerse()-1).getText() + " \n");
			} 
		} else {
			sb.append("Word not found!");
		}

		long endtime = System.currentTimeMillis();
		
		System.out.println(sb);
		System.out.println("Time Taken: " + ( endtime - starttime ) + " milliseconds");
	}

	/**
	 * Searches through books to find the verse which is needed
	 */
	public void lookupVerse(int bookId, int chapterNumber, int verseNumber) {
		StringBuilder sb = new StringBuilder();

		long startTime = System.currentTimeMillis();

		try {
			Verse answer = parsedBooks.get(bookId).getChapter(chapterNumber).getVerse(verseNumber-1);
			sb.append("Verse: " + answer.getText() + "\n");
		} catch (Exception e) {
			sb.append("Book/chapter/verse number is incorrect.\n");
		}

		long endTime = System.currentTimeMillis();

		System.out.println(sb);
		System.out.println("Time taken: " + (endTime - startTime) + " ms");
	}

	/**
	 * Searches through books to find the chapters which refer to the book and chapter number 
	 */
	public void lookupChapter(int bookId, int chapterNumber) {
		if(bookId >= 0 && chapterNumber > 0) {
			StringBuilder sb = new StringBuilder();

			long startTime = System.currentTimeMillis();

			try {
				Chapter chapterFound = parsedBooks.get(bookId).getChapter(chapterNumber);
				List<Verse> v = chapterFound.getVerses();

				sb.append("Chapter " + chapterFound.getChapterNumber()+1 + "\n");				
				if(chapterFound.getDescription() != null)
					sb.append("Description: " + chapterFound.getDescription() + "\n");

				for(Verse verse : v) {
					sb.append(verse.getText() + "\n"); // print out each verse
				}
			} catch (Exception e) {
				sb.append("Invalid chapter or book number");
			}

			long endTime = System.currentTimeMillis();

			System.out.println(sb);
			System.out.println("Time Taken: " + (endTime - startTime) + " ms");
		} else {
			System.out.println("Please enter a valid book ID and/or chapter number.\n");
		}
	}


	/**
	 * Searches through books and finds occurrences of search terms.
	 * 
	 * @param statementToSearch search term
	 */
	public void search(String statementToSearch) {
		StringBuilder sb = new StringBuilder();

		long start = System.currentTimeMillis();

		List<WordAppearance> appearances = wordIndex.getAppearances(statementToSearch);

		if(appearances != null && appearances.size() != 0) {
			sb.append("\"" + statementToSearch + "\" found! Occurances: " + appearances.size() + "\n");

			for(WordAppearance appearance : appearances) {
				if(appearance.descriptionType() == DescriptionType.NONE) {
					sb.append(appearance.getBook() + " [" + appearance.getChapter() + ":" + appearance.getVerse() + "] \n"); // repeated system outs are incredibly slow. this gives much better performance.
				} else if(appearance.descriptionType() == DescriptionType.CHAPTER) {
					sb.append(appearance.getBook() + " [" + appearance.getChapter() + ": CHAPTER/PSALM DESCRIPTION]\n");
				} else {
					sb.append(appearance.getBook() + ": BOOK DESCRIPTION\n"); // print this again - search results may be truncated if too long
				}
			}
			sb.append("Occurances: " + appearances.size() + "\n");
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
	 * @param isDescription true if line is a description
	 * 
	 * @return Verse if descriptionType is BOOK/CHAPTER, else NONE.
	 */
	private Verse parseLine(String line, String bookName, int verseNumber, int chapterNumber, DescriptionType descriptionType) {
		String[] words = StringUtils.splitWords(line);

		if(descriptionType == DescriptionType.NONE)
			words[0] = null; // first word is number if not a description, so ignore it

		for(String word : words) {
			if(word != null)
				logAppearance(word, bookName, verseNumber, chapterNumber, descriptionType);
		}

		if(descriptionType == DescriptionType.NONE)
			return new Verse(verseNumber, chapterNumber, line);

		return null;
	}

	/**
	 * Logs an appearance of a single word, given:
	 * @param word word to log
	 * @param bookName
	 * @param verseNumber
	 * @param chapterNumber
	 * @param descriptionType description enum (BOOK/CHAPTER/NONE/ETC.)
	 */
	private void logAppearance(String word, String bookName, int verseNumber, int chapterNumber, DescriptionType descriptionType) {
		wordIndex.addWord(word, bookName, verseNumber, chapterNumber, descriptionType);
	}
}
