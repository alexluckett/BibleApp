package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

/**
 * Completely unoptimised at this point. Did as a very basic concept of reading in the files.
 * 
 * @author Alex Luckett
 * @author Ashley Bridgwood
 *
 */
public class BibleApp {
	
	private static File destinationFolder = new File("data");
	private static File[] bookNames = destinationFolder.listFiles();
	//private static String[] books = {"1John.txt", "2Kings.txt", "3John.txt", "Colossians.txt", "1Timothy.txt", "Exodus.txt","1Chronicles.txt", "Amos.txt", "Hosea.txt" };
	
	public BibleApp() { }
	
	public static void main(String[] args) {
		
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < /*bookNames[i].length()*/ 58; i++){
			System.out.println("Current Book: " + bookNames[i].getName());
			readInFile(bookNames[i].getName());
		}
		
		//for(int i = 0; i < books.length; i++){
			//readInFile(books[i]);
		//}
		
		long endTime = System.currentTimeMillis();
				
		System.out.println("Total Time: " + (endTime - startTime) + " milliseconds");
	}
	
	private static void readInFile(String fileName) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/" + fileName));
			System.out.println("Processing: " + fileName);				
			long startTime = System.currentTimeMillis();
			
			Book book = new Book(reader.readLine()); // first line is always the book title
			
			String newLine = reader.readLine();
			int verseNumber = 1;
			
			int chapterNumber = 0;
			
			while(newLine != null) {
				if(newLine.startsWith("CHAPTER") || newLine.startsWith("PSALM")) {			
					chapterNumber++;
					verseNumber = 1;
				} else if(!newLine.equals("")) {
					book.addVerse(parseVerse(newLine, verseNumber, chapterNumber));
					verseNumber++;
				}
				
				newLine = reader.readLine();

			}
			
			long endTime = System.currentTimeMillis();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private static Verse parseVerse(String line, int verseNumber, int chapterNumber) throws ParseException {		
		String verseContent = line.substring(line.indexOf(" "));
		
		Verse verse = new Verse(verseNumber, chapterNumber);
		verse.addWord(verseContent);
		
		//System.out.println("Verse " + verseNumber + " parsed.");
		
		return verse;
	}

}