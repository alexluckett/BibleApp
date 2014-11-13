package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

/**
 * Completely unoptimised at this point. Did as a very basic concept of reading in the files.
 * 
 * @author Alex Luckett
 * @author Ashley Bridgwood
 *
 */
public class BibleApp {

	public BibleApp() { }
	
	public static void main(String[] args) {
		readInFile();
	}
	
	private static void readInFile() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("1Kings.txt"));
						
			long startTime = System.currentTimeMillis();
			
			Book book = new Book(reader.readLine()); // first line is always the book title
			
			String newLine = reader.readLine();
			int lineNumber = 2;
			
			int chapterNumber = 0;
			
			while(newLine != null) {
				if(newLine.startsWith("CHAPTER")) {			
					chapterNumber++;
				} else if(!newLine.equals("")) {
					book.addVerse(parseVerse(newLine, lineNumber, chapterNumber));
				}
				
				newLine = reader.readLine();
				lineNumber++;
			}
			
			long endTime = System.currentTimeMillis();
			
			System.out.println("Total time for 1 book: " + (endTime - startTime) + " milliseconds.");
			System.out.println();
			System.out.println();
			
			System.out.println("Book 1");
			System.out.println("============");
			System.out.println("Title      : " + book.getTitle());
			System.out.println("Verses   : " + book.getVerses().size() + " total");
			
			for(Verse bookVerse : book.getVerses()) {
				System.out.println("Chapter " + bookVerse.getChapterNumber() + ", verse " + bookVerse.getVerseNumber());
			}
			
			System.out.println("Total time for 1 book: " + (endTime - startTime) + " milliseconds.");
			
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
		
		System.out.println("Verse " + verseNumber + " parsed.");
		
		return verse;
	}

}