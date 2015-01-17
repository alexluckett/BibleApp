package main;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for bible app. Will include ability to load in bible,
 * search for text and go to certain chapters/verses via command line interface.
 * 
 * The Three Wise Men - Group 8:
 * @author Alexander Luckett  <lucketta@aston.ac.uk>
 * @author Ashley Bridgwood   <bridgwa1@aston.ac.uk>
 * @author Charandeep Rai     <raics@aston.ac.uk>
 */
public class BibleApp {
	private ParsedBible bible;

	/**
	 * Runs a new instance of the BibleApp.
	 *  
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		new BibleApp().displayMenuSystem();
	}

	/**
	 * Constructs a new BibleApp.
	 * 
	 * @author Alex
	 */
	public BibleApp() {		
		String errorMessage = "Failed to read in files. /data directory is empty or does not exist.";
		
		try {
			File[] bookNames = new File("data/").listFiles(); // list in the files in the "data" directory in the root path of project
			BibleReader bibleReader = new BibleReader();
			
			long startTime = System.currentTimeMillis();
			bible = bibleReader.readInBooks(bookNames);
			long endTime = System.currentTimeMillis();
				
			System.out.println("Read in time: " + (endTime - startTime) + " milliseconds.");
			System.out.println("Total unique words (case insensitive): " + bible.getWordIndex().uniqueWords());

		} catch (Exception e) {
			System.err.println(errorMessage);
			System.exit(0);
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
				menu.append("| 2. Lookup chapter            |\n");
				menu.append("| 3. Lookup verse              |\n");
				menu.append("| 4. Print verses by word      |\n");
				menu.append("| 5. Print range of verses     |\n");
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
				bible.printSearch(getWordInformation(sc));

				break;
			case 2:
				bible.printChapterLookup(getBookIdFromUser(sc), getChapterInformation(sc));

				break;
			case 3:
				bible.printVerseLookup(getBookIdFromUser(sc), getChapterInformation(sc), getVerseInformation(sc));

				break;
			case 4:
				bible.printVerseByWord(getWordInformation(sc));

				break;
			case 5:
				bible.printVerseRange(getBookIdFromUser(sc), getChapterInformation(sc), getVerseRangeInformation(sc));
				break;

			default:
				System.out.println("\nInvalid option!");
			}

		} while(!finished);

	}
	
	private String getStringInput(Scanner sc, String question) {
		System.out.println(question + ":");

		return sc.next();
	}
	
	private int getNumberInput(Scanner sc, String question) throws InputMismatchException {
		System.out.println(question + ":");

		return sc.nextInt();
	}

	/**
	 * Get a range of verse from the user
	 */
	public String getVerseRangeInformation(Scanner sc){
		return getStringInput(sc, "Please enter the range of verses you would like (EG: 1-5)");
	}
	/**
	 * Get words from the user
	 */
	public String getWordInformation(Scanner sc) {
		return getStringInput(sc, "Please enter the word you are looking for ");
	}

	/**
	 * Get chapter number from the user
	 */
	public int getChapterInformation(Scanner sc) {
		return getNumberInput(sc, "Please enter the chapter number");
	}

	/**
	 * Get verse number from the user
	 */
	public int getVerseInformation(Scanner sc) {
		return getNumberInput(sc, "Please enter the verse number");
	}

	/**
	 * Display the book selection to the user 
	 * 
	 * @author Ash
	 */
	public int getBookIdFromUser(Scanner sc) throws InputMismatchException {
		StringBuilder sb = new StringBuilder();
		
		List<Book> bibleBooks = bible.getBibleBooks();

		for(int i = 0; i < bibleBooks.size(); i++){
			sb.append((i+1) + ": " + bibleBooks.get(i).getTitle() + "\n"); // print out a list of book titles, numbered 1-n
		}

		return getNumberInput(sc, "Please choose a book number");
	}

	

	

	

	


	
	
	
}
