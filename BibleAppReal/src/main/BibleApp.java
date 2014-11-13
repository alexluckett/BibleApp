package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
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
	private static File[] bookNames = (new File("data")).listFiles(); // retrieves a list of all files within the data folder (source files)
	
	private static List<Book> parsedBooks = new ArrayList<Book>(66); // list of all fully parsed books
	
	public BibleApp() {}
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < bookNames.length; i++) {
			System.out.println("Current Book: " + bookNames[i].getName());
			readInFile(bookNames[i].getName());
		}
		
		//readInFile("2Kings.txt"); // 2 kings has a description. Here for debugging purposes - uncomment this and comment above to use.
		
		long endTime = System.currentTimeMillis();
				
		System.out.println("Total Time: " + (endTime - startTime) + " milliseconds");
	}
	
	private static void readInFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/" + fileName));
			System.out.println("Processing: " + fileName);			
			
			Book book = new Book(reader.readLine()); // first line is always the book title
						
			int verseNumber = 1;
			int chapterNumber = 0;
			
			String nextLine = reader.readLine();
			
			while(nextLine != null) {
				if(nextLine.startsWith("CHAPTER") || nextLine.startsWith("PSALM")) {			
					chapterNumber++;
					verseNumber = 1;
				} else if(!nextLine.equals("")) {
					book.addVerse(parseVerse(nextLine, verseNumber, chapterNumber));
					verseNumber++;
				}
				
				nextLine = reader.readLine();
			}
			
			parsedBooks.add(book);
		} catch (Exception e) {
			System.err.println("Error reading bible files. Please ensure package is up to date.");
			//e.printStackTrace();
		}
	}
	
	private static Verse parseVerse(String line, int verseNumber, int chapterNumber) throws ParseException {
		int firstWordEnd = line.indexOf(" ");
		
		String description = line.substring(0, firstWordEnd);
		String verseContent = line.substring(firstWordEnd); // remove verse number (always first string - we already know it at this point
		
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