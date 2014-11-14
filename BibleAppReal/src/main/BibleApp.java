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
	private static File[] bookNames = (new File("data")).listFiles(); // retrieves a list of all files within the data folder (source files)
	
	private static List<Book> parsedBooks = new ArrayList<Book>(66); // list of all fully parsed books
	
	public BibleApp() {}
	
	public static void main(String[] args) {
		BibleApp app = new BibleApp();
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < bookNames.length; i++) {
			System.out.println("Current Book: " + bookNames[i].getName());
			app.readInFile(bookNames[i].getName());
		}
		
		//app.readInFile("2Kings.txt"); // 2 kings has a description. Here for debugging purposes - uncomment this and comment above to use.
		
		long endTime = System.currentTimeMillis();
				
		System.out.println("Total Time: " + (endTime - startTime) + " milliseconds");
	}
	
	public void readInFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/" + fileName));
			System.out.println("Processing: " + fileName);			
			
			Book book = new Book(reader.readLine()); // first line is always the book title
						
			int verseNumber = 1;
			int chapterNumber = 0;
			
			String currentLine = reader.readLine();
			
			while(currentLine != null) {
				boolean isLineEmpty = currentLine.equals("");
				
				if(currentLine.startsWith("CHAPTER") || currentLine.startsWith("PSALM")) {			
					chapterNumber++;
					verseNumber = 1; // new chapter, must reset verse count to 1
				} else if(!isLineEmpty && verseNumber == 1 && Character.isLetter(currentLine.charAt(0))) { // description is always before first verse starts and starts with letter
					/* 
					 * Do we need descriptions? are they used in the program anywhere?
					 * It's a waste of processing power to bother with them if we don't need
					 * 
					 * If not: this currently breaks for PSALM 119, which 
					 * has a weird name (description?)each 8 lines or so. How are we going to deal with this?
					 * 
					 * TODO: plan this section
					 * 
					 * pseudo code:
					 * 
					 * if(currentLine is a new book) {
					 * 	  book.setDescription(newLine);
					 * } else {
					 *    chapter.setDescription(newLine);
					 * }
					 *
					 */
					System.out.println("DESCRIPTION FOUND: " + currentLine);
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
	
	public void search(String statementToSearch){
		//Cycling through books
		for(int i = 0; i < parsedBooks.size(); i++){
			//Cycling through verses
			for(int r = 0; i < parsedBooks.get(r).getVerses().size(); i++){
				
				Collection<Verse> verses = parsedBooks.get(r).getVerses();
				
				//if(parsedBooks.get(r).getVerses().)){
					
				//}
			}
		}
	}
	
	private Verse parseVerse(String line, int verseNumber, int chapterNumber) throws ParseException {
		String verseContent = line.substring(line.indexOf(" ")); // remove verse number (always first string - we already know it at this point
		
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